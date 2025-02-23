// src/pages/MyRoutines.tsx
import React, { useState, useEffect } from 'react';

interface Exercise {
  name: string;
  duration: number;
  rest: number;
}

interface Routine {
  id: string;
  name: string;
  exercises: Exercise[];
  dateCreated: string;
}

const MyRoutines: React.FC = () => {
  const [routines, setRoutines] = useState<Routine[]>([]);

  useEffect(() => {
    const routinesStr = localStorage.getItem('routines');
    if (routinesStr) {
      setRoutines(JSON.parse(routinesStr));
    }
  }, []);

  // Update a routine (used for reordering exercises)
  const updateRoutine = (updatedRoutine: Routine) => {
    const updatedRoutines = routines.map(r =>
      r.id === updatedRoutine.id ? updatedRoutine : r
    );
    setRoutines(updatedRoutines);
    localStorage.setItem('routines', JSON.stringify(updatedRoutines));
  };

  // Safely delete a routine after confirmation
  const handleDeleteRoutine = (routineId: string) => {
    if (window.confirm("Are you sure you want to delete this routine?")) {
      const updatedRoutines = routines.filter(routine => routine.id !== routineId);
      setRoutines(updatedRoutines);
      localStorage.setItem('routines', JSON.stringify(updatedRoutines));
    }
  };

  return (
    <main>
      <h1>My Routines</h1>
      {routines.length === 0 ? (
        <p>No routines found.</p>
      ) : (
        routines.map(routine => (
          <RoutineItem 
            key={routine.id} 
            routine={routine} 
            updateRoutine={updateRoutine} 
            deleteRoutine={handleDeleteRoutine} 
          />
        ))
      )}
    </main>
  );
};

interface RoutineItemProps {
  routine: Routine;
  updateRoutine: (routine: Routine) => void;
  deleteRoutine: (routineId: string) => void;
}

const RoutineItem: React.FC<RoutineItemProps> = ({ routine, updateRoutine, deleteRoutine }) => {
  const [exercises, setExercises] = useState(routine.exercises);
  const [draggedIndex, setDraggedIndex] = useState<number | null>(null);

  const onDragStart = (index: number) => {
    setDraggedIndex(index);
  };

  const onDragOver = (e: React.DragEvent<HTMLLIElement>, index: number) => {
    e.preventDefault();
    if (draggedIndex === null || draggedIndex === index) return;
    const newExercises = [...exercises];
    const draggedItem = newExercises[draggedIndex];
    newExercises.splice(draggedIndex, 1);
    newExercises.splice(index, 0, draggedItem);
    setDraggedIndex(index);
    setExercises(newExercises);
  };

  const onDragEnd = () => {
    updateRoutine({ ...routine, exercises });
    setDraggedIndex(null);
  };

  return (
    <div className="routine" style={{ position: 'relative' }}>
      {/* Delete Button */}
      <button 
        className="delete-btn" 
        onClick={() => deleteRoutine(routine.id)}
        title="Delete Routine"
      >
        X
      </button>
      <h2>{routine.name}</h2>
      <ul className="exercise-list">
        {exercises.map((ex, index) => (
          <li
            key={index}
            draggable
            onDragStart={() => onDragStart(index)}
            onDragOver={(e) => onDragOver(e, index)}
            onDragEnd={onDragEnd}
          >
            <strong>{ex.name}</strong> - Duration: {ex.duration} min, Rest: {ex.rest} sec
          </li>
        ))}
      </ul>
    </div>
  );
};

export default MyRoutines;
