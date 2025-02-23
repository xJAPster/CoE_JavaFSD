// src/pages/CreateRoutine.tsx
import React, { useState } from 'react';

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

const CreateRoutine: React.FC = () => {
  const [routineName, setRoutineName] = useState('');
  const [exercises, setExercises] = useState<Exercise[]>([]);
  const [exerciseName, setExerciseName] = useState('');
  const [duration, setDuration] = useState<number>(0);
  const [rest, setRest] = useState<number>(0);

  const addExercise = () => {
    if (exerciseName && duration > 0) {
      const newExercise: Exercise = {
        name: exerciseName,
        duration,
        rest,
      };
      setExercises([...exercises, newExercise]);
      setExerciseName('');
      setDuration(0);
      setRest(0);
    }
  };

  const saveRoutine = () => {
    if (!routineName || exercises.length === 0) {
      alert("Please enter a routine name and add at least one exercise.");
      return;
    }
    const newRoutine: Routine = {
      id: Date.now().toString(),
      name: routineName,
      exercises,
      dateCreated: new Date().toISOString(),
    };

    const routinesStr = localStorage.getItem('routines');
    const routines: Routine[] = routinesStr ? JSON.parse(routinesStr) : [];
    routines.push(newRoutine);
    localStorage.setItem('routines', JSON.stringify(routines));
    alert("Routine saved successfully!");
    setRoutineName('');
    setExercises([]);
  };

  return (
    <main>
      <h1>Create a New Routine</h1>
      <div className="form-group">
        <label>Routine Name:</label>
        <input
          type="text"
          value={routineName}
          onChange={(e) => setRoutineName(e.target.value)}
          placeholder="Enter routine name"
        />
      </div>
      <div className="exercise-form">
        <h2>Add Exercise</h2>
        <div className="form-group">
          <label>Exercise Name:</label>
          <input
            type="text"
            value={exerciseName}
            onChange={(e) => setExerciseName(e.target.value)}
            placeholder="Enter exercise name"
          />
        </div>
        <div className="form-group">
          <label>Duration (min):</label>
          <input
            type="number"
            value={duration}
            onChange={(e) => setDuration(parseInt(e.target.value))}
            placeholder="Enter duration in minutes"
          />
        </div>
        <div className="form-group">
          <label>Rest (sec):</label>
          <input
            type="number"
            value={rest}
            onChange={(e) => setRest(parseInt(e.target.value))}
            placeholder="Enter rest in seconds"
          />
        </div>
        <button onClick={addExercise}>Add Exercise</button>
      </div>
      
      {/* Added Exercises Section with grey background */}
      <div className="added-exercises-container">
        <h2>Exercises</h2>
        {exercises.length === 0 ? (
          <p>No exercises added yet.</p>
        ) : (
          <ul>
            {exercises.map((ex, index) => (
              <li key={index}>
                <strong>{ex.name}</strong> - Duration: {ex.duration} min, Rest: {ex.rest} sec
              </li>
            ))}
          </ul>
        )}
      </div>
      <button onClick={saveRoutine}>Save Routine</button>
    </main>
  );
};

export default CreateRoutine;
