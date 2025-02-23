import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import ProgressTracker from './pages/ProgressTracker';
import MyRoutines from './pages/MyRoutines';
import Home from './pages/Home';
import CreateRoutine from './pages/CreateRoutine';
import Header from './components/Header';

function App() {
  return (
    <Router>
      <Header />
      <div className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/create" element={<CreateRoutine />} />
          <Route path="/routines" element={<MyRoutines />} />
          <Route path="/progress" element={<ProgressTracker />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
