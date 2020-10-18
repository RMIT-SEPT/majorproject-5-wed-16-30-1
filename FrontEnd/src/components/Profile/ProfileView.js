import React from 'react';
import { Image, Jumbotron, Button, } from 'react-bootstrap';
import {Link} from 'react-router-dom';

const Style = {
    backgroundColor: '#eff0f2',
    paddingTop: '0px',
}
const Style2 = {
    width: "100%",
    height: "50%"
}
const ProfileView = (props) => {
    return (
        <div>
            <h2 style={{ textAlign: 'center' }}>Welcome  {props.name}</h2>
            <hr />
            <div style={Style} className="container col-md-2">
                <h2>Information</h2>
                <p><b>Name:</b>  {props.name}</p>
                <p><b>Email:</b>{props.email}</p>
                <p><b>Birthday:</b>{props.birthday}</p>
                <p><b>City:</b>{props.city}</p>
                <Link to='/eProfile.js'>Edit Profile</Link>
            </div>
            <hr />
            <div className="container col-md-8">    
            </div>
        </div>
    )
}
export { ProfileView };