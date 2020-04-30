import React, { Component, useCallback, useMemo } from 'react';
import { useDropzone } from 'react-dropzone'
import axios from "axios";

function Dropzone(props) {

  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);


    
    props.onUrlChange(URL.createObjectURL(acceptedFiles[0]))

    const formData = new FormData();
    formData.append("file", file);
    console.log(formData);

    // formData.append("file", file);
    // axios.post(`http://localhost:8080/add/temp-image-upload`,
    //   formData,
    //   {
    //     headers: {
    //       "Content-Type": "multipart/form-data"
    //     }
    //   }
    // ).then(() => {
    //   console.log("File uploaded successfully.")
    // }).catch(err => {
    //   console.log(err);
    // });

    // window.location.reload();

  }, [])

  const baseStyle = {
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    borderWidth: 2,
    borderRadius: 2,
    borderColor: '#eeeeee',
    borderStyle: 'dashed',
    backgroundColor: '#fafafa',
    color: '#bdbdbd',
    outline: 'none',
    transition: 'border .24s ease-in-out'
  };

  const activeStyle = {
    borderColor: '#2196f3'
  };

  const acceptStyle = {
    borderColor: '#00e676'
  };

  const rejectStyle = {
    borderColor: '#ff1744'
  };


  const {
    getRootProps,
    getInputProps,
    isDragActive,
    isDragAccept,
    isDragReject
  } = useDropzone({ onDrop });

  const style = useMemo(() => ({
    ...baseStyle,
    ...(isDragActive ? activeStyle : {}),
    ...(isDragAccept ? acceptStyle : {}),
    ...(isDragReject ? rejectStyle : {})
  }), [
    isDragActive,
    isDragReject
  ]);



  return (
    <div className="container">
      <div {...getRootProps({ style })}>
        <input {...getInputProps()} />
        <p>Drag and drop an image here, or click to select.</p>
      </div>
    </div>
  );
}

export default Dropzone;