  
import React, { Component } from 'react';



class Register extends Component{
	render(){
		return(
		<div>
		<li class="nav-item">
        <a class="nav-link" href="CustomerRegister.js">Register as new customer</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" href="OwnerRegister.js">Register as new owner</a>
        </li>
       
		</div>
		)
	}
}

export default Register;