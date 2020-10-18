import React, { Component } from 'react'
import { ownerRegister } from '../services';

class OwnerRegister extends Component {
    constructor(props) {
        super(props)

        this.state = {     
            username: "",
            password: "",
            checkpassowrd: "",
            errorMessage: "",
        }

        this.handleSubmit=this.handleSubmit.bind(this)
        this.usernameHandler = this.usernamehandler.bind(this);
        this.passwordhandler = this.passwordhandler.bind(this);
        this.checkpasswordhandler = this.checkpasswordhandler.bind(this);
        this.handlePostSubmit = this.handlePostSubmit.bind(this);
    }

    usernamehandler = (event) => {
        this.setState({
            username: event.target.value
        })
    }

    passwordhandler = (event) => {
        this.setState({
            password: event.target.value
        })
    }

    checkpasswordhandler = (event) => {
        this.setState({
            checkpassowrd: event.target.value
        })
    }

    handleSubmit = (event) => {
      event.preventDefault();
      ownerRegister(this.state.username, this.state.password, this.state.checkpassowrd, this.handlePostSubmit);
        
    }


    handlePostSubmit = (response) => { 
        console.log(response);
        const { history } = this.props;
        if(response.status === 201)
        {
			alert('Successful registration!');
            history.push('/OwnerLogin.js');
		}
        else
        {
			this.setState({errorMessage:response.data.message});
		}
    }

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <h1>Owner Registration</h1>
                    {this.state.errorMessage !== "" &&
                        <p>{this.state.errorMessage}</p>
                    }
                    <label>Username:</label> <input type="text" value={this.state.username} onChange={this.usernamehandler} placeholder="Username..." /><br />
                    
                    <label>Password :</label> <input type="password" value={this.state.password} onChange={this.passwordhandler} placeholder="Password..." /><br />
                    <label>Re-EnterPassword :</label> <input type="password" value={this.state.checkpassword} onChange={this.checkpasswordhandler} placeholder="Re enter Password..." /><br />
                    <input type="submit" value="submit" />
                </form>

            </div>
            
        )
    }
}

export default OwnerRegister
