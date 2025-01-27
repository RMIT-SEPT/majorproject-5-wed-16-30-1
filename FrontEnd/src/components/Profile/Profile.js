import React, { Component } from 'react';
import { ProfileView } from './ProfileView';
import { EditProfile } from './editProfile';
import { users } from './../data';

class CustomerLogin extends Component {
    constructor(props) {
        super(props)
        this.state = {
            name: '',
            email: '',
            birthday: '',
            city: '',
            password: '',
        }   
    }
    componentWillMount() {
        let checker = false;

        users.map(user => {
            if (user.email === localStorage.getItem('email') && user.password === localStorage.getItem('password') && checker === false) {
                checker = true;
                var userParser = JSON.parse(JSON.stringify(user))
                return this.setState({
                    ...userParser                                                                               //spread operator
                }, function () {
                    console.log(".....................................................");
                    console.log(this.state);
                });
            }
        })
        if (checker === false) {
            alert('You Must Login');
        }
        checker = false;
    }
    handleInputChange = (e) => {
        this.setState({
            [e.target.name]: (e.target.value)
        })
    }
    handleSubmit = () => {
        const userString = {
            name: this.state.name,
            email: this.state.email,
            password: this.state.password,
            birthday: this.state.birthday,
            city: this.state.city,
        }
        let check = false
        let Users = users.map(user => {
            if (user.email === userString.email && user.password === userString.password && check === false) {
                check = true
                alert ('Profile Edited')
                return Object.assign({}, user, userString)
            }
            return user;
        })
       
        if (check === false) {
           alert ('Enter Correct Password Again');
        }
        check = false
        console.log(Users)
    }
    render() {
        return (
            <div>
                <ProfileView name={this.state.name}
                    email={this.state.email}
                    birthday={this.state.birthday}
                    city={this.state.city}
                /> 
            </div>
        );
    }
}

export default CustomerLogin;



