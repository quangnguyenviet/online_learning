import styles from './Login.module.scss';
import StorageService from 'service/StorageService';
import { Link, useNavigate } from 'react-router-dom';
import React, { useState } from 'react';
import AuthApi from 'service/apis/AuthApi';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
import { ErrorDisplay } from 'components/common/ErrorDisplay/ErrorDisplay';

export default function Login() {
    const navigate = useNavigate();
    const { showError, dismissError, errorMessage } = useError();
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        const formData = new FormData(e.target);
        const data = Object.fromEntries(formData.entries());

        try {
            const response = await AuthApi.login(data);
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
            else{
                console.log('Login failed:', response);
            }
        } catch (error) {
            showError(error.response?.data?.message || 'Đã có lỗi xảy ra trong quá trình đăng nhập.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <>
        <ErrorDisplay message={errorMessage} onDismiss={dismissError} />
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
                    <button type="submit" disabled={isLoading}>
                        {isLoading ? 'Đang đăng nhập...' : 'Đăng nhập'}
                    </button>
                </div>

                <div className={styles['form-footer']}>
                    <a href="#">Quên mật khẩu?</a>
                    {/* <p>Chưa có tài khoản?</p> */}
                    <ul className={styles['signup-links']}>
                        <li><Link to="/signup/student">Đăng ký học sinh</Link></li>
                        <li><Link to="/signup/instructor">Đăng ký giảng viên</Link></li>
                    </ul>

                </div>
            </form>
        </div>
        </>
       
    );
}
