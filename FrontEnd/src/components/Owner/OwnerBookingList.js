import React, { Component } from 'react'

class OwnerBookingList extends Component {
	render() { 
	return(
<div className="container">
                            <div className="card card-body bg-light mb-3">
                                <div className="row">
                                    <div className="col-lg-6 col-md-4 col-8">
                                        <h3>Worker Management</h3>
                                        <p>You can see worker profile or update worker's information and delete the worker.</p>
                                    </div>
                                    <div className="col-md-4 d-none d-lg-block">
                                        <ul className="list-group">
                                            <a href="#">
                                                <li className="list-group-item board">
                                                    <i className="fa fa-flag-checkered pr-1">Worker Profile </i>
                                                </li>
                                            </a>
                                            <a href="#">
                                                <li className="list-group-item update">
                                                    <i className="fa fa-edit pr-1">Update Worker Information</i>
                                                </li>
                                            </a>
                                            <a href="">
                                                <li className="list-group-item delete">
                                                    <i className="fa fa-minus-circle pr-1">Delete Worker</i>
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

export default OwnerBookingList;