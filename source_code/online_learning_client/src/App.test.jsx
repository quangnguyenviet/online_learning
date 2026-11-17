import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { MemoryRouter } from 'react-router-dom';
import App from './App';
import allReducers from '@/reducers';

test('renders the app', () => {
  const store = createStore(allReducers);
  const { container } = render(
    <Provider store={store}>
      <MemoryRouter>
        <App />
      </MemoryRouter>
    </Provider>
  );
  expect(container).toBeTruthy();
});
