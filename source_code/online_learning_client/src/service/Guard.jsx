import { Navigate, useLocation } from "react-router-dom";

const isStudent = () => {
    const roles = localStorage.getItem('role');
    return roles && roles.includes('STUDENT');
}
export const isInstructor = () => {
    const roles = localStorage.getItem('role');
    return roles && roles.includes('INSTRUCTOR');
}
export const isAdmin = () => {
    const roles = localStorage.getItem('role');
    return roles && roles.includes('ADMIN');
}

export const isLoggedIn = () => {
    const token = localStorage.getItem('token');
    return !!token;
}

export const StudentRoute = ({element: Component}) => {
   
    const location = useLocation();
    
    return isStudent() ? (
        Component
    ):(
        <Navigate to="/login" replace state={{from: location}}/>
    )
}
export const InstructorRoute = ({element: Component}) => {
   
    const location = useLocation();
    return isInstructor() ? (
        Component
    ):(
        <Navigate to="/login" replace state={{from: location}}/>
    )
}
export const AdminRoute = ({element: Component}) => {
   
    const location = useLocation();
    return isAdmin() ? (
        Component
    ):(
        <Navigate to="/login" replace state={{from: location}}/>
    )
}