import { Navigate, Outlet } from "react-router-dom";

export default function PrivateRouter(){
    const token = localStorage.getItem('token');
    const isLogin = token ? true : false;

    return (
        <>
            {isLogin ? <Outlet /> : <Navigate to="/login" />} 
        </>
    );
}