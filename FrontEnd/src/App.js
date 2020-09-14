import React, { Component } from "react";
import { HashRouter, Switch, Route } from "react-router-dom";

import Home from "./pages/Home";
import CustomerLogin from "./pages/CustomerLogin";

class App extends Component{
	render(){
		return(
		<HashRouter>
			<Switch>
				<Route exact path="/" component={ Home } />
				<Route exact path="/login/customer" component={ CustomerLogin } />
			</Switch>
		</HashRouter>
		)
	}
}

export default App;
