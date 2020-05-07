import React from 'react';
import './App.css';
import axios from "axios";

import Button from 'react-bootstrap/Button'


const Action = ({ dbid, username, userProfileId, message}) => {

    return (
        <div className="Action">
            <img src={`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/display/${dbid}/image`} className="Img-action" />
            <ul>       
                <li><h3>{username}</h3></li>
                <li><p>ID : {userProfileId}</p></li>
                <li><p>{message}</p></li>              
                <br />
            </ul>
        </div>
    )
};

export default Action