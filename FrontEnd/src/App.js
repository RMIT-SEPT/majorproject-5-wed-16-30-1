import React, { Component } from "react";
import { HashRouter, Switch, Route } from "react-router-dom";

import Home from "./Home";
import WorkerLogin from "./WorkerLogin";

class App extends Component{
	render(){
		return(
		<HashRouter>
			<Switch>
				<Route exact path="/" component={Home} />
		
				<Route exact path="/login/worker" component={WorkerLogin} />
			</Switch>
		</HashRouter>
		)
	}
}

export default App;
