import React, { Component } from 'react'
import CustomerBookingList from './Customer/CustomerBookingList'
import BookingButton from './Customer/BookingButton';

class CustomerDashboard extends Component {
	render() {
		return(
<div className="Customer">
		 <div className="container">
		  <div className="row">
		   <div className="col-md-12">
		    <h1 className="display-4 text-center">Manage your Booking</h1>
		    <br />
		<br />
		<BookingButton/>
		<br />
		<hr />
		<CustomerBookingList/>
</div>
</div>
</div>
</div>
				  
		)

}
}

export default CustomerDashboard;