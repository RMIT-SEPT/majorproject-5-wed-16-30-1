import React, {Component } from 'react'

class Header extends Component {
	render() {
		return (
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="Dashboard.js">Dashboard</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="Home">Home <span class="sr-only">(current)</span></a>
      </li>
	    <li class="nav-item">
        <a class="nav-link" href="CustomerBooking.js">Booking</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="Login">Log in</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="Register.js">Sign up</a>
      </li>
    </ul>
  </div>
</nav>
			 
		)
	}
}

export default Header;