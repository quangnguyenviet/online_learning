export const searchReducer = (state = "", action) => {
  switch (action.type) {
    case 'SEARCH':
      console.log("action.payload", action.payload);

      return action.payload.key;
  
    default:
      return state;
  }
}