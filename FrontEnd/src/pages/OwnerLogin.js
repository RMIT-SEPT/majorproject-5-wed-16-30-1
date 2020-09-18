import React, { Component } from 'react';
import { useHistory } from "react-router-dom";
import { ownerLogin } from "../services.js";

class OwnerLogin extends Component{

	constructor(props){
		super(props);

		this.state ={
			username:"",
			password:"",
			errorMessage: ""

		};

		this.handleUsernameChange = this.handleUsernameChange.bind(this);
		this.handlePasswordChange = this.handlePasswordChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handlePostSubmit = this.handlePostSubmit.bind(this);
	}

	handleUsernameChange(event){
		this.setState({username:event.target.value});
	}

	handlePasswordChange(event){
        this.setState({password: event.target.value});
    }

	handleSubmit(event){
        ownerLogin(this.state.username,this.state.password,this.handlePostSubmit);
    }

    handlePostSubmit(response)
    {
		let history = useHistory(); 
		alert(response);
        if(response.status === 200)
        {
			history.push('/ownerdashboard');
		}
        else
        {
			this.setState({errorMessage:response.data.message});
		}
	}

	render()
	{
		return(
			<div>
                <p>/login/owner</p>
                {this.state.errorMessage !== "" &&
                    <p>{this.state.errorMessage}</p>
                }
                <form onSubmit={this.handleSubmit}>
                <input type="text" onChange={ this.handleUsernameChange } />
                <br />
                <input type="password" onChange={ this.handlePasswordChange } />
                <br />
                <button type="submit">Login</button>
                </form>
		</div>
			);
	}
}

export default OwnerLogin;