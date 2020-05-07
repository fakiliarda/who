import React, { useState, useEffect } from 'react';
import './App.css';

import Member from './Member';
import axios from "axios";

const ListAllMembersPage = () => {

    const [members, setMembers] = useState([]);

    useEffect(() => {

        axios.get(`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/list/`).then(res => {
                setMembers(res.data)     
                console.log(res.data)
            });
        console.log('rendered')
    }, [members.userProfileId]);

    return (
        <div className = "ListAllMembersPage">
            {members.map(member => (
                <Member username={member.username}  userProfileId={member.userProfileId} key={member.userProfileId}/>
            ))}
        </div>
    );  
};

export default ListAllMembersPage