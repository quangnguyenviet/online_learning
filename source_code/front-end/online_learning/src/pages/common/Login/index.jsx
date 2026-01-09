import styles from './Login.module.scss';
import StorageService from 'service/StorageService';
import { Link, useNavigate } from 'react-router-dom';
import React from 'react';
import AuthApi from 'service/apis/AuthApi';
import Swal from 'sweetalert2';

export default function Login() {
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());

        AuthApi.login(data)
            .then((response) => {
                if (response.status === 1000) {
                    const data = response.data;
                    StorageService.saveToken(data.token);
                    StorageService.saveRole(data.roles);
                    if (data.roles.includes('ADMIN')) {
                        navigate('/admin/dashboard');
                    }
                    else if (data.roles.includes('INSTRUCTOR')) {
                        navigate('/instructor/dashboard');
                    }
                    else if (data.roles.includes('STUDENT')) {
                        navigate('/');
                    }
                }
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    };

    return (

        <div className={styles['login-container']}>
            <Link to="/" className={styles['back-to-home']}>← Trang chủ</Link>

            <form onSubmit={handleSubmit} className={styles['login-form']}>
                <h2>Đăng nhập hệ thống</h2>
                <p>Vui lòng nhập tên đăng nhập và mật khẩu</p>

                <div className={styles['form-group']}>
                    <input type="email" name="email" placeholder="Email" required />
                </div>

                <div className={styles['form-group']}>
                    <input type="password" name="password" placeholder="Mật khẩu" required />
                </div>

                <div className={styles['form-actions']}>
                    <button type="submit">Đăng nhập</button>
                </div>

                <div className={styles['form-footer']}>
                    <a href="#">Quên mật khẩu?</a>
                    {/* <p>Chưa có tài khoản?</p> */}
                    <ul className={styles['signup-links']}>
                        <li><Link to="/signup/student">Đăng ký học sinh</Link></li>
                        <li><Link to="/signup/instructor">Đăng ký giảng viên</Link></li>
                    </ul>

                    {/* <p><Link to="/" className="back-home">← Quay về trang chủ</Link></p> */}
                </div>
            </form>
        </div>
    );
}
