import React from 'react'
import {link, Link} from "react-router-dom";

 const CreateWorkerButton=() => {
    return (
	<React.Fragment>
		<Link to="/addWorker" className="btn btn-lg btn-info">
		Add a Worker 
		</Link>
		</React.Fragment>
    )
};
export default CreateWorkerButton;