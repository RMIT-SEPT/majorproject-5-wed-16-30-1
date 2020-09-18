import React from 'react'
import {link, Link} from "react-router-dom";

 const ViewButton=() => {
    return (
	<React.Fragment>
		<Link to="/View" className="btn btn-lg btn-info">
		View your Schedule
		</Link>
		</React.Fragment>
    )
};
export default ViewButton;