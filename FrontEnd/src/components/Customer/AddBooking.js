import React, { Component } from 'react'


class AddBooking extends Component {

        constructor(props){
            super(props);
    
            this.state= {
            servicename: "",
            serviceid: "",
            desc: "",
            start_date: "",
            end_date: ""
         
        }; 

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
        
    }
    
    
        onChange(e){
            this.setState({[e.target.name]: e.target.value});
        }

       serviceidhandler = (event) => {
            this.setState({
                serviceid: event.target.value
            })
        }

        onSubmit(e){
            alert(`${this.state.servicename}   Registered Successfully !!!!`)
            e.preventDefault();
            const newBooking = {
                servicename: this.state.servicename,
                serviceid: this.state.serviceid,
                desc: this.state.desc,
                start_date:this.state.start_date,
                end_date: this.state.end_date  
            }
    
            
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
                                name="servicename"
                                value= {this.state.servicename}
                                onChange = {this.onChange}
                                />
                                
                            </div>
                            <div className="form-group">
                             
                            </div>

                            <div className="form-group">
                                <input type="text" className="form-control form-control-lg" 
                                placeholder="service ID"
                                name ="serviceid"
                                value= {this.state.serviceid}
                                onChange = {this.onChange}
                                    />
                            </div>
                          
                          

                            <div className="form-group">
                                <textarea className="form-control form-control-lg" 
                                placeholder="service description"
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

                            <h6>Estimated End Date</h6>
                            <div className="form-group">
                                <input type="date" className="form-control form-control-lg" 
                             name="end_date" 
                                value= {this.state.end_date}
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