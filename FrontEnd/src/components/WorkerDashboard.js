import React, { Component } from 'react'
import WorkerBookingList from './Worker/WorkerBookingList'
import ViewButton from './Worker/ViewButton';

class WorkerDashboard extends Component {
	render() {
		return(
<div className="Worker">
		 <div className="container">
		  <div className="row">
		   <div className="col-md-12">
		    <h1 className="display-4 text-center">Manage your personal info</h1>
		    <br />
		<br />
		<ViewButton/>
		<br />
		<hr />
		<WorkerBookingList/>
</div>
</div>
</div>
</div>
				  
		)

}
}

export default WorkerDashboard;