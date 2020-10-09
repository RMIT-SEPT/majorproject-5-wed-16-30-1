import React, { Component } from 'react'



class OwnerRegister extends Component {
    constructor(props) {
        super(props)

        this.state = {
           
            username: "",
            password: "",
            checkpassowrd: "",


        }
        this.handleSubmit=this.handleSubmit.bind(this)
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
        alert(`${this.state.username}   Registered Successfully !!!!`)
        console.log(this.state);
        this.setState({
            username: "",
            password: "",
            checkpassword: "",
            
        })
     event.preventDefault()
        
    }




    render() {
        return (
            <div>

                <form onSubmit={this.handleSubmit}>
                    <h1>Owner Registration</h1>
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