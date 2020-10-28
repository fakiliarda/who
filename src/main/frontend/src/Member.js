import React from 'react';
import './App.css';
import axios from "axios";

import Button from 'react-bootstrap/Button'


const Member = ({ username, userProfileId }) => {

    const handleClick = () => {
        console.log('Deleting user...');
        // axios.post(`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/delete/${userProfileId}`).then(() => {
        axios.post(`http://localhost:5000/user/delete/${userProfileId}`).then(() => {

            console.log('User deleted.');
            window.location.reload();
        }).catch(err => {
            console.log("User could not deleted.");
        });

    };

    return (
        <div className="Member">
            {/* <img src={`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/list/${userProfileId}/image`} className="Img-add" /> */}
            <img src={`http://localhost:5000/user/list/${userProfileId}/image`} className="Img-add" />
            <ul>
                <li><h3>{username}</h3></li>
                <li><p>ID : {userProfileId}</p></li>
                <br />
                <Button variant="outline-secondary" onClick={handleClick}>Delete</Button>
            </ul>

        </div>
    )
};

export default Member