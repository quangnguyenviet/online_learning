// Jest-dom matchers (for Vitest)
import * as matchers from '@testing-library/jest-dom/matchers';
import { expect } from 'vitest';

expect.extend(matchers);

// react-modal calls Modal.setAppElement('#root') on import, which requires a
// #root element in the DOM. jsdom ships with an empty body, so create one.
if (typeof document !== 'undefined' && !document.getElementById('root')) {
  document.body.innerHTML = '<div id="root"></div>';
}
