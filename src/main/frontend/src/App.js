import React, { useState, useEffect, useCallback } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios";
import { useDropzone } from 'react-dropzone'
import Button from 'react-bootstrap/Button'

const UserProfiles = () => {

  const [UserProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data)
    });
  }

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return UserProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        <br />
        {userProfile.userProfileId ? (
          <img
            src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`}
          />
        ) : null}
        <br />
        <br />
        <h1>{userProfile.username}</h1>
        <p>{userProfile.userProfileId}</p>
        <Dropzone {...userProfile} />
        <br />
      </div>
    );
  });
};

const MainPage = () => {

  return (<>
    <Button variant="primary">Add Member to Home</Button>{' '}
    <br />
    <Button variant="primary">List All Members</Button>{' '}
    <br />
    <Button variant="primary">Display Member Actions</Button>{' '}
  </>)



};

function Dropzone({ userProfileId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("File uploaded successfully.")
    }).catch(err => {
      console.log(err);
    });

  }, [])
  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop })

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag and drop an image, or click to select...</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <MainPage />
    </div>
  );
}

export default App;
