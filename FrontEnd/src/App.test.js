import React from 'react';
import WorkerLogin from './pages/WorkerLogin';
import View from './components/Worker/View';
import OwnerBookingList from './components/Owner/OwnerBookingList';
import Home from './pages/Home';
import {shallow, mount} from  "enzyme";
import Enzyme from  "enzyme";
import Adapter from  "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });


describe('WorkerLogin component', () => {
  it('has text "Login"', () => {
    const workerlogin = shallow(<WorkerLogin />);
    expect(workerlogin.containsMatchingElement(<p>/login/worker</p >)).toEqual(true);
  });
}
);

describe('Home component', () => {
  it('has text "This is home page"', () => {
    const home = shallow(<Home />);
    expect(home.containsMatchingElement(<p>This is home page</p >)).toEqual(true);
  });
}
);

describe('OwnerBookingList component', () => {
  it('has text "You can see worker profile or update workers information and delete the worker"', () => {
    const ownerbookinglist = shallow(<OwnerBookingList />);
    expect(ownerbookinglist.containsMatchingElement(<p>You can see worker profile or update worker's information and delete the worker.</p>)).toEqual(true);
  });
}
);

describe('View component', () => {
  it('has text "This is your time table"', () => {
    const view = shallow(<View />);
    expect(view.containsMatchingElement(<p>This is your time table</p>)).toEqual(true);
  });
}
);