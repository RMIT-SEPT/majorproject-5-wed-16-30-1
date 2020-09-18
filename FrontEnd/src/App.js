import React from 'react';
import './App.css';
import OwnerDashboard from './components/OwnerDashboard';
import WorkerDashboard from './components/WorkerDashboard';
import Header from './components/Layout/Header';
import "bootstrap/dist/css/bootstrap.min.css";
import CustomerLogin from './pages/CustomerLogin';
import WorkerLogin from './pages/WorkerLogin';
import CustomerRegister from './pages/CustomerRegister';
import OwnerRegister from './pages/OwnerRegister';
import {BrowserRouter as Router, Route} from "react-router-dom";
import AddWorker from './components/Owner/AddWorker';
import View from './components/Worker/View';
import Home from './pages/Home';
import Login from './pages/Login';
import OwnerLogin from './pages/OwnerLogin';
import Register from './pages/Register';

function App() {
  return (
    <Router>
    <div>
     <Header/>
     
     <Route exact path="/Register.js" component={Register} />
     <Route exact path="/CustomerLogin.js" component={CustomerLogin} />
     <Route exact path="/WorkerLogin.js" component={WorkerLogin} />
     <Route exact path="/OwnerLogin.js" component={OwnerLogin} />
	    <Route exact path="/ownerdashboard" component={OwnerDashboard} />
      <Route exact path="/workerdashboard" component={WorkerDashboard} />
      <Route exact path="/addWorker" component={AddWorker} />
      <Route exact path="/Home" component={Home} />
      <Route exact path="/Login" component={Login} />
      <Route exact path="/View" component={View} />
      <Route exact path="/CustomerRegister.js" component={CustomerRegister} />
      <Route exact path="/OwnerRegister.js" component={OwnerRegister} />
    </div>
    </Router>
  );
}

export default App;
