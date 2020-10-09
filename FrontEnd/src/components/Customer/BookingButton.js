import React from 'react'
import {link, Link} from "react-router-dom";

 const BookingButton=() => {
    return (
	<React.Fragment>
		<Link to="/addBooking" className="btn btn-lg btn-info">
		Make a Booking
		</Link>
		</React.Fragment>
    )
};
export default BookingButton;