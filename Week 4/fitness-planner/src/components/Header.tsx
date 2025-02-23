// src/components/Header.tsx
import React from 'react';
import { NavLink } from 'react-router-dom';
import traintrackLogo from '../assets/traintrack_logo.png';

const Header: React.FC = () => {
  return (
    <header>
      <nav>
        <ul>
          <li>
            <NavLink 
              to="/" 
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              Home
            </NavLink>
          </li>
          <li>
            <NavLink 
              to="/create" 
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              Create Routine
            </NavLink>
          </li>
          <li>
            <NavLink 
              to="/routines" 
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              My Routines
            </NavLink>
          </li>
          <li>
            <NavLink 
              to="/progress" 
              className={({ isActive }) => (isActive ? "active" : "")}
            >
              Progress Tracker
            </NavLink>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
