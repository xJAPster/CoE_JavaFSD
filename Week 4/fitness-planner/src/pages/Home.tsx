// src/pages/Home.tsx
import React from 'react';
import traintrackLogo from '../assets/traintrack_logo.png';

const Home: React.FC = () => {
  return (
    <main>
      <h1>Welcome to TrainTrack !</h1>
      <p>Create and track your custom set workout routines.</p>
      {}
      <img 
        src={traintrackLogo} 
        alt="TrainTrack Logo" 
        className="home-logo-large" 
      />
    </main>
  );
};

export default Home;
