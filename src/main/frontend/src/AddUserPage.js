import React, { useState } from 'react';
import './App.css';

import Button from 'react-bootstrap/Button'
import TextField from '@material-ui/core/TextField';
import ImageDropzone from './ImageDropzone';
import axios from "axios";
import EmptyProfileImage from './profileImage.png';


const AddUserPage = () => {


  const [textFieldValue, setTextFieldValue] = useState('');

  const [userProfileImage, setUserProfileImage] = useState({
    url: EmptyProfileImage,
    file: null
  })

  const [error, setError] = useState({
    items: [],
    errorMessage: ''
  });

  const [info, setInfo] = useState({
    items: [],
    infoMessage: ''
  });

  const handleTextFieldChange = (e) => {
    setTextFieldValue(e.target.value);
  }

  const handleProfileImageChange = ({ url, file }) => {
    setUserProfileImage({
      url: url,
      file: file
    });
  }

  const handleErrorChange = ({ message }) => {
    setError({
      errorMessage: message
    });
  }

  const handleInfoChange = ({ message }) => {
    setInfo({
      infoMessage: message
    });
  }

  const handleSave = () => {

    if (!textFieldValue) {
      console.log('Username could not be empty.');
      const message = "Username could not be empty.";
      handleErrorChange({ message });
    } else if (!userProfileImage.file) {
      console.log('Image could not be empty.')
      const message = "Image could not be empty.";
      handleErrorChange({ message });
    } else {
      const message = '';
      handleErrorChange({ message });

      const formData = new FormData();
      formData.append("file", userProfileImage.file);
      console.log(userProfileImage.file)
      // axios.post(`http://who-env.eba-cpey3tte.eu-central-1.elasticbeanstalk.com:5000/user/add/${textFieldValue}/`,
      axios.post(`http://localhost:5000/user/add/${textFieldValue}/`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      ).then(() => {
        console.log("File uploaded successfully.")
        const message = "User added successfully.";
        handleInfoChange({ message });

        const url = EmptyProfileImage;
        const file = null;
        const e = '';

        handleProfileImageChange({url, file});
        setTextFieldValue('');
      }).catch(err => {
        console.log(err);
        const message = "Connection could not be established.";
        handleErrorChange({ message });
      });
    }
  };

  return (

    < div className="AddUserPage">

      <img src={userProfileImage.url} className="Img-add" style={{ marginBottom: 10 }} />
      <ImageDropzone userProfileImage={userProfileImage} onUrlChange={handleProfileImageChange} style={{ marginTop: 30 }} />
      <TextField
        required
        id="outlined-required"
        label="Name"
        // defaultValue="Name"
        variant="outlined"
        value={textFieldValue} onChange={handleTextFieldChange}
        style={{ marginTop: 30 }} />
      <br />
      <Button variant="primary" size="lg" onClick={handleSave} style={{ marginTop: 30, marginBottom: 15 }}>Save</Button>{' '}
      <div>
        {error.errorMessage &&
          <p style={{ color: "red" }} className="error">
            {error.errorMessage}
          </p>}
      </div>
      <div>
        {info.infoMessage &&
          <p style={{ color: "green" }} >
            {info.infoMessage}
          </p>}
      </div>
    </div >
  );

}

export default AddUserPage