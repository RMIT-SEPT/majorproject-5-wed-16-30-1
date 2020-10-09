import React, { Component } from 'react';
import './CustomerView.css';


   


class CustomerView extends Component{
  
      constructor(props){
       super(props);

      this.state= {
        services: [
          { id: 1, name: 'Wasif', age: 21, email: 'wasif@email.com' },
          { id: 2, name: 'Ali', age: 19, email: 'ali@email.com' },
          { id: 3, name: 'Saad', age: 16, email: 'saad@email.com' },
          { id: 4, name: 'Asad', age: 25, email: 'asad@email.com' }
       ]
   
      }; 
      
    }

    deleteHandler= (id) => {
  
      const services = this.state.services.filter(service => service.id !== id);
      this.setState({ services: services});
    }
 
    renderTableData() {
      return this.state.services.map((services, index) => {
         const { id, name, age, email } = services 
         return (
            <tr key={id}>
               <td>{id}</td>
               <td>{name}</td>
               <td>{age}</td>
               <td>{email}</td>
               <td className='opration'>
               <button className='button' onClick={() => this.deleteHandler(id)}>Delete</button>
                    </td>
            </tr>
         )
      })
   }
    


    renderTableHeader() {
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