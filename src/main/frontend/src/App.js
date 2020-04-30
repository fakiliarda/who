import React from 'react';
import './App.css';

import { BrowserRouter as Router, Route } from "react-router-dom";
import MainPage from './MainPage'
import AddUserPage from './AddUserPage'

function App() {

  return (
    <Router>
      <div className="App" style={{
        position: 'absolute', left: '50%', top: '50%',
        transform: 'translate(-50%, -90%)'
      }}>

        <Route path="/" exact strict render={
          () => {
            return <MainPage />
          }
        } />

        <Route path="/add" render={
          () => {
            return <AddUserPage />
          }
        } />

        <Route path="/list" render={
          () => {
            return <h1>Hello list</h1>
          }
        } />


        <Route path="/display" render={
          () => {
            return <h1>Hello display</h1>
          }
        } />

      </div>
    </Router>
  );
}

export default App;
