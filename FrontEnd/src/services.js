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