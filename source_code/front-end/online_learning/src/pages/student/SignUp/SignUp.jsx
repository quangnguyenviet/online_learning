import { useState } from 'react';
import styles from './Signup.module.scss';
import { Link } from 'react-router-dom';
import UserApi from 'service/apis/UserApi';
import { useNavigate } from 'react-router-dom';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
import { ErrorDisplay } from 'components/common/ErrorDisplay/ErrorDisplay';
export default function Signup() {
  const navigate = useNavigate();
  const { showError, dismissError, errorMessage } = useError();
  const [isLoading, setIsLoading] = useState(false);

  const createUser = async (data) => {
    try {
      const response = await UserApi.createUser(data);
      navigate('/login');
    } catch (error) {
      console.error('Error creating user:', error);
      showError(error.response?.data?.message || 'Đã xảy ra lỗi khi tạo tài khoản');
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    console.log(data);

    if (data.password !== data.password2) {
      showError('Mật khẩu và xác nhận mật khẩu không khớp');
      return;
    }

    delete data.password2; // delete confirm password field

    setIsLoading(true);
    await createUser(data);
    setIsLoading(false);
  };

  return (
    <>
    <ErrorDisplay message={errorMessage} onDismiss={dismissError} />
      <div className={styles.signupContainer}>
      
      <Link to="/" className={styles.backToHome}>← Trang chủ</Link>

      <form className={styles.signupForm} onSubmit={handleSubmit}>
        <h2>Tạo tài khoản mới</h2>
        <p>Vui lòng nhập thông tin bên dưới để đăng ký</p>

        <div className={styles.formGroup}>
          <label htmlFor="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Nhập email" required />
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="password">Mật khẩu</label>
          <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required />
        </div>

        <div className={styles.formGroup}>
          <label htmlFor="confirmPassword">Nhập lại mật khẩu</label>
          <input type="password" id="confirmPassword" name="password2" placeholder="Nhập lại mật khẩu" required />
        </div>


        <button type="submit" className={styles.btnSubmit} disabled={isLoading}>
          {isLoading ? 'Đang đăng ký...' : 'Đăng ký'}
        </button>
        <div className={styles.formFooter}>
          <p>Đã có tài khoản? <Link to="/login">Đăng nhập</Link></p>
        </div>

      </form>
    </div>
    </>
    
  );
}
