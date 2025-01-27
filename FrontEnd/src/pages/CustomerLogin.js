import React, { Component } from 'react';
import { customerLogin } from "../services.js";

class CustomerLogin extends Component {
	constructor(props) {
		super(props);
		
	this.state = {
		username: "",
		password: "",
		errorMessage: ""
	};
	this.handleUsernameChange = this.handleUsernameChange.bind(this);
	this.handlePasswordChange = this.handlePasswordChange.bind(this);
	this.handleSubmit = this.handleSubmit.bind(this);
	this.handlePostSubmit = this.handlePostSubmit.bind(this);
	}
	
	handleUsernameChange(event) {
		this.setState({ username: event.target.value });
	}
	handlePasswordChange(event) {
		this.setState({ password: event.target.value});
	}
	handleSubmit(event) {
        event.preventDefault();
		customerLogin(this.state.username, this.state.password, this.handlePostSubmit);
	}
	handlePostSubmit(response) {
		if (response.status === 200) {
            localStorage.setItem('customerUsername', this.state.username);
			this.props.history.push('/customerdashboard');
		}
		else {
			this.setState({ errorMessage: response.data.message });
		}
	}
	render() {
		return(
		<div>
		  <p>/login/customer</p>
		  {this.state.errorMessage !== "" &&
		  <p>{this.state.errorMessage}</p>
	}
		<form onSubmit={ this.handleSubmit}>
		<input type="text" onChange={ this.handleUsernameChange }/>
		<br />
		<input type="password" onChange={ this.handlePasswordChange }/>
		<br />
		<button type="submit">Login</button>
		</form>
		</div>
		);
	}
}

export default CustomerLogin;
