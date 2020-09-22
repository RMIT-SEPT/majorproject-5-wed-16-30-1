import React, { Component } from 'react'
import OwnerBookingList from './Owner/OwnerBookingList'
import CreateWorkerButton from './Owner/CreateWorkerButton';

class OwnerDashboard extends Component {
	render() {
		return(
<div className="Owner">
		 <div className="container">
		  <div className="row">
		   <div className="col-md-12">
		    <h1 className="display-4 text-center">Manage Workers</h1>
		    <br />
		<br />
		<CreateWorkerButton/>
		<br />
		<hr />
		<OwnerBookingList/>
</div>
</div>
</div>
</div>
				  
		)

}
}

export default OwnerDashboard;