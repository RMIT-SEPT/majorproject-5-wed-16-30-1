import React, { Component } from 'react'
import BookingList from './Worker/BookingList'
import CreateWorkerButton from './Worker/CreateWorkerButton';

class Dashboard extends Component {
	render() {
		return(
<div className="Worker">
		 <div className="container">
		  <div className="row">
		   <div className="col-md-12">
		    <h1 className="display-4 text-center">New Worker</h1>
		    <br />
		<br />
		<CreateWorkerButton/>
		<br />
		<hr />
		<BookingList/>
</div>
</div>
</div>
</div>
				  
		)

}
}

export default Dashboard;