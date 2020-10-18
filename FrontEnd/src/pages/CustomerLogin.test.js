import React from 'react';
import CustomerLogin from './CustomerLogin.js';

import {shallow, mount} from  "enzyme";
import Enzyme from  "enzyme";
import Adapter from  "enzyme-adapter-react-16";

Enzyme.configure({adapter: new Adapter() });


describe('Customer Login button', () => {
    it('has click event', () => {
        const handleSubmit = jest.fn();

        const button = shallow((<CustomerLogin onClick={handleSubmit}>Login</CustomerLogin>));
        button.find('button').simulate('click');
        expect(handleSubmit.mock.calls.length).toEqual(1);
    });
  }

  );

 