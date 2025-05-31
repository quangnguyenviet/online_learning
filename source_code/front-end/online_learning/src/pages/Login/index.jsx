import './Login.scss';
import { setData } from '../../service/localStorageService';
import { Link, useNavigate } from 'react-router-dom';
import React from 'react';
import { authenticate } from '../../utils/AuthUtil';
import Swal from 'sweetalert2';

export default function Login() {
    const URL_AUTH = 'http://localhost:8080/online_learning/auth/login';
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());

        authenticate(URL_AUTH, 'POST', data)
            .then((data) => {
                if (data.status === 1000) {
                    setData('token', data.data.token);
                    setData('role', data.data.role);
                    setData('id', data.data.id);
                    navigate(data.data.role === 'INSTRUCTOR' ? '/instructor/dashboard' : '/');
                }
                else if (data.status === 1001) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Đăng nhập thất bại',
                        text: 'Tên đăng nhập hoặc mật khẩu không đúng!',
                    });
                }
                else {
                    alert('Đăng nhập thất bại!');
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    return (

        <div className="login-container">
            <Link to="/" className="back-to-home">← Trang chủ</Link>

            <form onSubmit={handleSubmit} className="login-form">
                <h2>Đăng nhập hệ thống</h2>
                <p>Vui lòng nhập tên đăng nhập và mật khẩu</p>

                <div className="form-group">
                    <input type="text" name="username" placeholder="Tên đăng nhập" required />
                </div>

                <div className="form-group">
                    <input type="password" name="password" placeholder="Mật khẩu" required />
                </div>

                <div className="form-group checkbox-group">
                    <input type="checkbox" name="role" value="INSTRUCTOR" id="role" />
                    <label htmlFor="role">Tôi là giảng viên</label>
                </div>

                <div className="form-actions">
                    <button type="submit">Đăng nhập</button>
                </div>

                <div className="form-footer">
                    <a href="#">Quên mật khẩu?</a>
                    {/* <p>Chưa có tài khoản?</p> */}
                    <ul className="signup-links">
                        <li><Link to="/signup/student">Đăng ký học sinh</Link></li>
                        <li><Link to="/signup/instructor">Đăng ký giảng viên</Link></li>
                    </ul>

                    {/* <p><Link to="/" className="back-home">← Quay về trang chủ</Link></p> */}
                </div>
            </form>
        </div>
    );
}
