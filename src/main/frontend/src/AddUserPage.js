import React, { Component, useState } from 'react';
import './App.css';

import Button from 'react-bootstrap/Button'
import TextField from '@material-ui/core/TextField';
import ImageDropzone from './ImageDropzone';
import axios from "axios";
import EmptyProfileImage from './profileImage.png';


const AddUserPage = () => {

  const [textFieldValue, setTextFieldValue] = useState('');
  const [userProfileImage, setUserProfileImage] = useState(EmptyProfileImage)

  const handleTextFieldChange = (e) => {
    setTextFieldValue(e.value);
  }

  const handleProfileImageChange = url => {
    setUserProfileImage(url)
  }

  return (

    < div className="AddUserPage" style={{
      marginTop: '400px'
    }}>

      <img src={userProfileImage} className="Img-add" />
      <br />
      <br />
      <ImageDropzone userProfileImage={userProfileImage} onUrlChange={handleProfileImageChange} />
      <br />
      <TextField
        required
        id="outlined-required"
        label="Name"
        // defaultValue="Name"
        variant="outlined"
        value={textFieldValue} onChange={handleTextFieldChange}
      />
      <br />
      <br />
      <Button variant="primary" size="lg" onClick={() =>
        axios.post(`http://localhost:8080/add/user-profile/${textFieldValue}`).then(res => {
          console.log(res);
        })
      }>Save</Button>{' '}
    </div >
  );

}

export default AddUserPage