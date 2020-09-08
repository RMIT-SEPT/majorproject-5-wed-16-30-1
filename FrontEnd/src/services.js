import axios from 'axios';

export let workerLogin = (username, password, callback) =>{
	axios.post('/api/auth/worker/login',{
		'username':username,
		'password':password
		}
		)
	.then((response)=>{
		callback(response);
	})
	.catch((error)=>{
		callback(error.response);
	});
}
