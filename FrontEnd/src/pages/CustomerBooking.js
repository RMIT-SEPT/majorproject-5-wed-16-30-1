import React, { Component } from 'react'
import PropTypes from "prop-types";


class CustomerBooking extends Component {
    constructor(){
        super();

        this.state= {
        name: "",
        customerIdentifier: "",
        desc: "",
        start_date: "",
		email:"",
		phonenumber:"",
     
    }; 
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    
        }

    onChange(e){
        this.setState({[e.target.name]: e.target.value});
    }
    onSubmit(e){
        e.preventDefault();
        const newBooking = {
            name: this.state.name,
            customerIdentifier: this.state.customerIdentifier,
            desc: this.state.desc,
            start_date:this.state.start_date,
            email: this.state.email,
			phonenumber: this.state.phonenumber,
        }

        this.props.createBooking(newBooking, this.props.history);
    }
    render() {
        return (
            <div className="Customer">
            <div className="container">
                <div className="row">
                    <div className="col-md-8 m-auto">
                        <h5 className="display-4 text-center">Create / Edit Customer form</h5>
                        <hr />
                        <form onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg " 
                                placeholder="Name" 
                                name="name"
                                value= {this.state.name}
                                onChange = {this.onChange}
                                />
                                
                            </div>
                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg" 
                                placeholder="Unique Customer ID"
                                name="customerIdentifier"
                                value= {this.state.customerIdentifier}
                                onChange = {this.onChange}
                                    />
                            </div>
                          
                            <div className="form-group">
                                <textarea className="form-control form-control-lg" 
                                placeholder="Person Description"
                                name = "desc"
                                value= {this.state.desc}
                                onChange = {this.onChange}
                                />
                                

                            </div>
                            <h6>Start Date</h6>
                            <div className="form-group">
                                <input type="date" className="form-control form-control-lg" 
                                name="start_date"
                                value= {this.state.start_date}
                                onChange = {this.onChange}
                                />
								
								
                            </div>
                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg" 
                                name="email" 
                                value= {this.state.email}
                                onChange = {this.onChange}
                                />
							
							</div>
                            <div className="form-group">
                                <input type="number" className="form-control form-control-lg" 
                                name="phonenumber" 
                                value= {this.state.phonenumber}
                                onChange = {this.onChange}
                                />
                            </div>
    
                            <input type="submit" className="btn btn-primary btn-block mt-4" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
        )
    }
}
CustomerBooking.propTypes = {
    createProject: PropTypes.func.isRequired
  };
  
  export default CustomerBooking;