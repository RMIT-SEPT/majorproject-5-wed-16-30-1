import axios from 'axios';

export let customerLogin = (username, password, callback) => {
  axios.post('/api/auth/customer/login',{
		'username':username,
		'password':password
		})
	.then((response)=>{
		callback(response);
	})
	.catch((error)=>{
		callback(error.response);
	});
}

export let workerLogin = (username, password, callback) => {
		axios.post('/api/auth/worker/login',{
			'username':username,
			'password':password
			})
		.then((response)=>{
			callback(response);
		})
		.catch((error)=>{
			callback(error.response);
		});
}


export let ownerLogin = (username, password, callback) => {
	axios.post('/api/auth/owner/login',{
		'username':username,
		'password':password
		})
	.then((response)=>{
		callback(response);
	})
	.catch((error)=>{
		callback(error.response);
	});
}

export let ownerRegister = (username, password,checkPassword, callback) => {
	axios.post('/api/auth/owner/register',{
		'username':username,
		'password':password,
		'checkPassword':checkPassword,
		})
	.then((response)=>{
		callback(response);
	})
	.catch((error)=>{
		callback(error.response);
	});
}

export let customerRegister = (username, password,checkPassword, callback) => {
	axios.post('/api/auth/customer/register',{
		'username':username,
		'password':password,
		'checkPassword':checkPassword,
		})
	.then((response)=>{
		callback(response);
	})
	.catch((error)=>{
		callback(error.response);
	});
}

export let getBookings = (customerUsername, callback) => {
  axios.get('/api/booking/' + customerUsername)
    .then(res => {
      callback(res);
    })
    .catch(err => {
      callback(err.response);
    });
}

export let addBooking = (booking, callback) => {
  booking.serviceDate = booking.serviceDate + 'T00:00:00Z';
  axios.put('/api/booking/', {
      serviceID: booking.serviceID,
      customerUsername: booking.customerUsername,
      workerName: booking.workerName,
      serviceName: booking.serviceName,
      serviceDate: booking.serviceDate,
      duration: booking.duration,
    })
    .then(res => {
      callback(res);
    })
    .catch(err => {
      callback(err.response);
    });
}

export let delBooking = (booking, callback) => {
  axios.delete('/api/booking/', {
      serviceID: booking.serviceID,
      customerUsername: booking.customerUsername,
      workerName: booking.workerName,
      serviceName: booking.serviceName,
      serviceDate: booking.serviceDate + 'T00:00:00Z',
      duration: booking.duration,
    })
    .then(res => {
      callback(res);
    })
    .catch(err => {
      callback(err.response);
    });
}

