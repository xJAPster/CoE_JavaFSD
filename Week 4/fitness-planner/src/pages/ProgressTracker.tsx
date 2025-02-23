// src/pages/ProgressTracker.tsx
import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const ProgressTracker: React.FC = () => {
  const chartRef = useRef<HTMLCanvasElement>(null);
  const chartInstance = useRef<Chart | null>(null);

  useEffect(() => {
    // If a chart instance exists, destroy it before creating a new one.
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    const labels = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
    const dataPoints = labels.map(() => Math.floor(Math.random() * 60) + 20);

    if (chartRef.current) {
      chartInstance.current = new Chart(chartRef.current, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [{
            label: 'Workout Duration (min)',
            data: dataPoints,
            backgroundColor: 'rgba(39, 174, 96, 0.2)',
            borderColor: 'rgba(39, 174, 96, 1)',
            borderWidth: 2,
            fill: true,
            tension: 0.3,
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: { beginAtZero: true }
          }
        }
      });
    }

    // Cleanup function to destroy chart instance when component unmounts
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
        chartInstance.current = null;
      }
    };
  }, []); // Empty dependency array ensures this runs only once on mount

  return (
    <main>
      <h1>Progress Tracker</h1>
      <canvas ref={chartRef} id="progressChart"></canvas>
    </main>
  );
};

export default ProgressTracker;
