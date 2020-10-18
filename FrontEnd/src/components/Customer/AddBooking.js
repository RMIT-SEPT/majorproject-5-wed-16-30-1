import React, { Component } from 'react'

import { addBooking } from '../../services';

class AddBooking extends Component {

        constructor(props){
            super(props);
    
            this.state= {
              serviceID: '',
              customerUsername: localStorage.getItem('customerUsername'),
              workerName: 'default',
              serviceName: '',
              serviceDate: '',
              duration: 60,

              description: '',
              endDate: ''
        }; 

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        this.handleResponse= this.handleResponse.bind(this);
    }
    
    
        onChange(e){
            this.setState({[e.target.name]: e.target.value});
        }

        onSubmit(e){
            e.preventDefault(); 
            addBooking(this.state, this.handleResponse);
            console.log(this.state);
        }

        handleResponse(res) {
          console.log(res);
          alert(res.data.message);
        }

     render(){
		 return(  
			<div className="Person">
            <div className="container">
                <div className="row">
                    <div className="col-md-8 m-auto">
                        <h5 className="display-4 text-center">Make a Booking</h5>
                        <hr />
                        <form onSubmit={this.onSubmit}>
                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg " 
                                placeholder="Service Name"
                                name="serviceName"
                                value={this.state.serviceName}
                                onChange={this.onChange}
                                />
                                
                            </div>
                            <div className="form-group">
                             
                            </div>

                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg" 
                                placeholder="service ID"
                                name ="serviceID"
                                value= {this.state.serviceID}
                                onChange = {this.onChange}
                                    />
                            </div>
                          
                          

                            <div className="form-group">
                                <textarea className="form-control form-control-lg" 
                                placeholder="service description"
                                name = "description"
                                value= {this.state.description}
                                onChange = {this.onChange}
                                />
                                

                            </div>
                            <h6>Start Date</h6>
                            <div className="form-group">
                                <input type="date" className="form-control form-control-lg" 
                           name="serviceDate"
                           value= {this.state.serviceDate}
                           onChange = {this.onChange}
                                />
                            </div>

                            <h6>Estimated End Date</h6>
                            <div className="form-group">
                                <input type="date" className="form-control form-control-lg" 
                             name="endDate" 
                                value= {this.state.endDate}
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

  
  export default AddBooking
