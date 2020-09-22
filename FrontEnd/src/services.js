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
