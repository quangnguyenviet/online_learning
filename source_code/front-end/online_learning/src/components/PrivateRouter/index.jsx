import { Navigate, Outlet } from "react-router-dom";
import { isLoggedIn } from "service/Guard";

export default function PrivateRouter(){
    const isLogin = isLoggedIn();

    return (
        <>
            {isLogin ? <Outlet /> : <Navigate to="/login" />} 
        </>
    );
}