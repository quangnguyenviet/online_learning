import { useState } from 'react';
import styles from './Signup.module.scss';
import { signup } from 'utils/AuthUtil';
import Swal from 'sweetalert2';
import { Link } from 'react-router-dom';

export default function Signup() {
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    if (data.password !== data.password2) {
      setError('Mật khẩu không khớp');
      return;
    }

    setError('');
    delete data.confirmPassword; // Xóa field này trước khi gửi

    signup(data)
      .then((response) => {
        if (response.status === 1000) {
          Swal.fire({
            title: 'Đăng ký thành công',
            text: 'Bạn đã đăng ký tài khoản thành công. Vui lòng đăng nhập để tiếp tục.',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          window.location.href = '/login';
        }
        else if (response.status === 1004) {
          setError('Tên đăng nhập đã tông tại. Vui lòng chọn tên khác.');
        }
        else if (response.status === 1005) {
          setError('Email đã được sử dụng. Vui lòng sử dụng email khác.');
        }
        else {
          setError(response.message || 'Đăng ký không thành công. Vui lòng thử lại.');
        }
      })
      .catch((err) => {
        console.error('Error during signup:', err);
        setError('Đã xảy ra lỗi. Vui lòng thử lại sau.');
      });
  };

  return (
    <div className={styles.signupContainer}>
      <Link to="/" className={styles.backToHome}>← Trang chủ</Link>

      <form className={styles.signupForm} onSubmit={handleSubmit}>
        <h2>Tạo tài khoản mới</h2>
        <p>Vui lòng nhập thông tin bên dưới để đăng ký</p>

        <div className={styles.formGroup}>
          <label htmlFor="username">Tên đăng nhập</label>
          <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" required />
        </div>

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

        {error && <p className={styles.errorMessage}>{error}</p>}

        <button type="submit" className={styles.btnSubmit}>Đăng ký</button>
        <div className={styles.formFooter}>
          <p>Đã có tài khoản? <Link to="/login">Đăng nhập</Link></p>
        </div>

      </form>
    </div>
  );
}
