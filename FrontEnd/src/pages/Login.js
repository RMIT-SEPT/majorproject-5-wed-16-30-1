  
import React, { Component } from 'react';



class Login extends Component{
	render(){
		return(
		<div>
		<li class="nav-item">
        <a class="nav-link" href="WorkerLogin.js">Login as worker</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" href="CustomerLogin.js">Login as customer</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" href="OwnerLogin.js">Login as owner</a>
        </li>
		</div>
		)
	}
}

export default Login;