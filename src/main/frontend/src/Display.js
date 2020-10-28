import React, { useState, useEffect, useRef } from 'react';
import './App.css';

import axios from "axios";
import Action from './Action';
import WebViewer from '@pdftron/webviewer'

const Display = () => {

  const [actions, setActions] = useState([]);
  const viewer = useRef(null);


  useEffect(() => {

    console.log('useEffect');
    WebViewer(
      {
        path: './webviewer/lib',
        initialDoc: 'https://pdftron.s3.amazonaws.com/downloads/pl/webviewer-demo.pdf',
        // css: './App.css'
      },
      viewer.current,
    ).then((instance) => {
      console.log('yes');
      const { docViewer } = instance;


      instance.openElements(['leftPanel']);


    });

    // axios.get(`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/display/`).then(res => {
    // axios.get(`http://localhost:5000/user/display/`).then(res => {
    //   setActions(res.data)
    //   console.log(res.data)
    // }).catch(err => {
    //   console.log(err);
    //   const message = "Connection could not be established.";
    // });
    // console.log('rendered')

  }, []);

  return (
    // <div className="Display">
    //   {actions.map(action => (
    //     <Action dbid={action.dbid} username={action.userProfile.username} userProfileId={action.userProfile.userProfileId} message={action.message} key={action.userProfile.userProfileId} />
    //   ))}
    // </div>
    
    <div className="Display">
      <div className="header">Sample</div>
      <div className="webviewer" ref={viewer}></div>
    </div>
  );
};

export default Display