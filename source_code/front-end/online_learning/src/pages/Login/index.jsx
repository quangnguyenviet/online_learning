import './Login.scss';
import { setData } from '../../service/localStorageService';
import { useNavigate } from 'react-router-dom';
export default function Login() {
    const navigate = useNavigate();
    const handleLogin = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());
        fetch('http://localhost:8080/online_learning/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
            .then((response) => response.json())
            .then((data) => {
                if(data.status === 1000){
                    setData('token', data.data.token);
                    navigate('/');
                }
            })
    }
    return (
        <>
            <div className="login">
            <div className="container">
                <div className="content">
                    <h1>Login</h1>
                    <form onSubmit={handleLogin}>
                        <div className="form-group">
                            <input type="text" className="form-control" name="username" placeholder="Username" />
                        </div>
                        <div className="form-group">
                            <input type="password" name="password" placeholder="Password" className="form-control" />
                        </div>
                        <button type='submit' className="btn btn-primary" id="loginBtn">Sign in</button>
                    </form>
                </div>
            </div>
            </div>
            
        </>
    );
}