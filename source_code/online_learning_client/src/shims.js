// Shim Node globals missing in the browser.
// CRA (webpack) used to provide these implicitly; Vite does not.
// `sockjs-client` (CommonJS, pulled in by the WebSocket lesson features)
// references `global` at module-load time and crashes without this.
//
// This file must be imported before any library that needs the shim —
// it is the first import in index.jsx.
if (typeof window !== 'undefined' && window.global === undefined) {
  window.global = window;
}
