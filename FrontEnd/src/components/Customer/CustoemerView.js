import React, { Component } from 'react';
import './CustomerView.css';   

import { getBookings } from '../../services';


class CustomerView extends Component{
  
      constructor(props){
       super(props);

      this.state= {
        services: [] 
      }; 
     
        this.handleResponse = this.handleResponse.bind(this);
        this.renderTableHeader = this.renderTableHeader.bind(this);
    }

    componentDidMount() {
      getBookings(localStorage.getItem('customerUsername'), this.handleResponse);
    }

    handleResponse(services) {
      console.log(services);
      if (services !== undefined) {
        this.setState({ services: services.data });
      }
    }

    deleteHandler= (service) => {
  
      const services = this.state.services.filter(s => s.serviceID !== service.serviceID);
      this.setState({ services: services});
    }
 
    renderTableData() {
      if (!Array.isArray(this.state.services)) {
        return (<div></div>);
      }
      console.log(this.state.services);
      if (this.state.services == undefined || this.state.services.length == 0) {
        return (<div></div>);
      }

      return this.state.services.map((service, index) => { 
         return (
            <tr key={service.serviceID}>
               <td>{service.serviceID}</td>
               <td>{service.customerUsername}</td>
               <td>{service.workerName}</td>
               <td>{service.serviceName}</td>
               <td>{service.serviceDate}</td>
               <td>{service.duration}</td>
               <td className='opration'>
               <button className='button' onClick={() => this.deleteHandler(service)}>Delete</button>
                    </td>
            </tr>
         )
      })
   }
    


    renderTableHeader() {
      if (!Array.isArray(this.state.services)) {
        return (<div></div>);
      }
      if (this.state.services == undefined || this.state.services.length == 0) {
        return (<div></div>);
      }

      let header = Object.keys(this.state.services[0])
      return header.map((key, index) => {
         return <th key={index}>{key.toUpperCase()}</th>
      })
   }



	render(){
		return(

      <div>
      <h1 id='title'>Customer Bookings</h1>
      <table id='service'>
         <tbody>
         <tr>{this.renderTableHeader()}</tr>
             {this.renderTableData()}
         </tbody>
      </table>
   </div>
	
		)
	}
}

export default CustomerView;
