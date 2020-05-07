import React, { useState, useEffect } from 'react';
import './App.css';

import axios from "axios";
import Action from './Action';

const Display = () => {

    const [actions, setActions] = useState([]);

    useEffect(() => {

        axios.get(`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/display/`).then(res => {
            setActions(res.data)
            console.log(res.data)
        }).catch(err => {
            console.log(err);
            const message = "Connection could not be established.";
          });
        console.log('rendered')
        
    }, []);

    return (
        <div className="Display">
            {actions.map(action => (
                <Action dbid={action.dbid} username={action.userProfile.username} userProfileId={action.userProfile.userProfileId} message={action.message} key={action.userProfile.userProfileId}/>
            ))}
        </div>
    );
};

export default Display