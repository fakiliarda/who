import React from 'react';
import Button from 'react-bootstrap/Button'
import { Link } from "react-router-dom";

const MainPage = () => {

  return (
    <div>
      <Link to="add">
        <Button variant="outline-primary" size="lg">Add Members to Home</Button>{' '}
      </Link>
      <br />
      <br />
      <br />
      <Link to="list">
        <Button variant="outline-primary" size="lg">List All Members</Button>{' '}
      </Link>
      <br />
      <br />
      <br />
      <Link to="display">
        <Button variant="outline-primary" size="lg">Display Member Actions</Button>{' '}
      </Link>
    </div>)
};

export default MainPage