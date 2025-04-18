import { Navigate, Outlet } from "react-router-dom";

export default function InstructorRouter() {
    const ROLE = localStorage.getItem('role');


    return (
        <>
            {ROLE === 'INSTRUCTOR' ? (
                <Outlet />
            ) : (
                <Navigate to="/132423asdf" />
            )}
        </>
    );
}