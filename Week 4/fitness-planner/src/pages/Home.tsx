// src/pages/Home.tsx
import React from 'react';
import traintrackLogo from '../assets/traintrack_logo.png';

const Home: React.FC = () => {
  return (
    <main>
      <h1>Welcome to Your Fitness Planner</h1>
      <p>Create and track your custom workout routines.</p>
      {/* Logo placed below the text */}
      <img 
        src={traintrackLogo} 
        alt="TrainTrack Logo" 
        className="home-logo-large" 
      />
    </main>
  );
};

export default Home;
