import React, { Component } from 'react'

class WorkerBookingList extends Component {
	render() { 
	return(
<div className="container">
                            <div className="card card-body bg-light mb-3">
                                <div className="row">
                                    <div className="col-lg-6 col-md-4 col-8">
                                        <h3>update your schedule</h3>
                                        <p>You can make and view your booking here.</p>
                                    </div>
                                    <div className="col-md-4 d-none d-lg-block">
                                        <ul className="list-group">
                                            <a href="#">
                                                <li className="list-group-item board">
                                                    <i className="fa fa-flag-checkered pr-1">Update your profile</i>
                                                </li>
                                            </a>
                                            <a href="#">
                                                <li className="list-group-item update">
                                                    <i className="fa fa-edit pr-1">view your booking</i>
                                                </li>
                                            </a>
                                            <a href="">
                                                <li className="list-group-item delete">
                                                    <i className="fa fa-minus-circle pr-1">make a schedule change request</i>
                                                </li>
                                            </a>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
	)
	}
}

export default WorkerBookingList;