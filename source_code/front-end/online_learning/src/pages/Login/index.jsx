import './Login.scss';
import { setData } from '../../service/localStorageService';
import { Link, useNavigate } from 'react-router-dom';
import React from 'react';
import { authenticate } from '../../utils/AuthUtil';

export default function Login() {
    const URL_AUTH = 'http://localhost:8080/online_learning/auth/login';
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        // Xử lý đăng nhập ở đây
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());
        console.log(data);
        

        authenticate(URL_AUTH, 'POST', data)
            .then((data) => {
                if (data.status === 1000) {
                    setData('token', data.data.token);
                    setData('role', data.data.role);
                   
                    if (data.data.role === 'INSTRUCTOR') {
                        navigate('/instructor/dashboard');
                    }
                    else {
                        navigate('/');
                    }
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });


        // fetch('http://localhost:8080/online_learning/auth/login', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json',
        //     },
        //     body: JSON.stringify(data)
        // })
        //     .then((response) => response.json())
        //     .then((data) => {
        //         if (data.status === 1000) {

        //             setData('token', data.data.token);
        //             if (data.data.role === 'student') {
        //                 navigate('/');
        //             }
        //             if (data.data.role === 'instructor') {
        //                 navigate('/instructor/dashboard');
        //             }
                    
        //         }
        //     })

    };

    return (
        <>
            <div className="login">
                <form onSubmit={handleSubmit} className="login__form">
                    <h1>login</h1>
                    <p>Please enter your login and password!</p>
                    <input type="text" name="username" placeholder="Username" required />
                    <input type="password" name="password" placeholder="Password" required />
                    <div>
                        <input type="checkbox" name="role" value="INSTRUCTOR" id="role" /> <label htmlFor="role">I am an intructor</label>
                    </div>

                    <a className="forgot" href="#">Forgot password?</a>

                    <input type="submit" value="Login" />
                    <p>Don't have an account? <Link to="/signup">Signup</Link></p>
                </form>
            </div>
        </>
    );
};