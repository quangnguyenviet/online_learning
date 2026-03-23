import StorageService from "service/StorageService";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthApi from "service/apis/AuthApi";
export default function Logout() {
    const navigate = useNavigate();
    const logout = async () => {
        try {
            const logoutRequest = {
                token: StorageService.getToken()
            }
            const data = await AuthApi.logout(logoutRequest);
            if(data.status === 1000){
                StorageService.removeUserData();
                navigate("/login");
            }
            
        } catch (error) {
            console.error("Logout failed:", error);
        }
    };

    useEffect(() => {
        logout();
        
    }, []);
}