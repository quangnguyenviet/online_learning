import { courseReducer } from "./courseReducer";
import { searchReducer } from "./searchReducer";
import { combineReducers } from "redux";

const allReducers = combineReducers({
    course: courseReducer,
    search: searchReducer,
    // Add other reducers here
});

export default allReducers;