import CustomerLogin from "./pages/CustomerLogin";

test('Submit response should be 200', () => {
	const {response} = '200';
	expect(response === '200').toBeTruthy();
});

test('Username should be have more than 8 letter', () => {
	expect(functions.getIntArray(8)).toHaveLength(8);
)};

test('Password should be have more than 8 letter', () => {
	expect(functions.getIntArray(8)).toHaveLength(8);
)};