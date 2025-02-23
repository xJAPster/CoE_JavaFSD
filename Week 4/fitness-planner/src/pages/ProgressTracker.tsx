// src/pages/ProgressTracker.tsx
import React, { useEffect, useRef, useState } from 'react';
import Chart from 'chart.js/auto';

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

interface ChartData {
  labels: string[];
  data: number[];
}

const ProgressTracker: React.FC = () => {
  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstance = useRef<Chart | null>(null);
  const [chartData, setChartData] = useState<ChartData>({ labels: [], data: [] });

  
  useEffect(() => {
    const routinesStr = localStorage.getItem('routines');
    let routines: Routine[] = routinesStr ? JSON.parse(routinesStr) : [];

    if (routines.length > 0) {
      
      const dataMap: { [key: string]: number } = {};
      routines.forEach(routine => {
        const date = new Date(routine.dateCreated).toLocaleDateString();
        const totalDuration = routine.exercises.reduce((sum, ex) => sum + ex.duration, 0);
        dataMap[date] = (dataMap[date] || 0) + totalDuration;
      });
      
      const labels = Object.keys(dataMap).sort((a, b) => new Date(a).getTime() - new Date(b).getTime());
      const data = labels.map(label => dataMap[label]);
      setChartData({ labels, data });
    } else {
      
      setChartData({ labels: [], data: [] });
    }
  }, []);

  
  useEffect(() => {
    
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }
    
    if (chartRef.current && chartData.labels.length > 0) {
      chartInstance.current = new Chart(chartRef.current, {
        type: 'line',
        data: {
          labels: chartData.labels,
          datasets: [
            {
              label: 'Total Workout Duration (min)',
              data: chartData.data,
              backgroundColor: 'rgba(39, 174, 96, 0.2)',
              borderColor: 'rgba(39, 174, 96, 1)',
              borderWidth: 2,
              fill: true,
              tension: 0.3,
            },
          ],
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    }
    
    // Cleanup on unmount or when chartData changes
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
        chartInstance.current = null;
      }
    };
  }, [chartData]);

  return (
    <main>
      <h1>Progress Tracker</h1>
      {chartData.labels.length === 0 ? (
        <p>No workout data to display. Please add a routine first.</p>
      ) : (
        <canvas ref={chartRef} id="progressChart"></canvas>
      )}
    </main>
  );
};

export default ProgressTracker;
