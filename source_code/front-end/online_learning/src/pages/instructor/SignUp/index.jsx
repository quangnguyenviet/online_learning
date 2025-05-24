import React, { useState } from 'react';
import './style.scss';

export default function SignupInstructor() {
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    if (data.password !== data.confirmPassword) {
      setError('Mật khẩu không khớp');
      return;
    }

    setError('');
    data.role = 'INSTRUCTOR';
    delete data.confirmPassword;

    fetch('http://localhost:8080/online_learning/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    })
      .then((res) => res.json())
      .then((resData) => {
        if (resData.status === 1000) {
          alert('Đăng ký giảng viên thành công!');
        } else {
          alert('Đăng ký thất bại: ' + resData.message);
        }
      });
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={handleSubmit}>
        <h2>Đăng ký tài khoản giảng viên</h2>
        <p>Vui lòng nhập thông tin bên dưới</p>

        <div className="form-group">
          <label htmlFor="username">Tên đăng nhập</label>
          <input type="text" id="username" name="username" required />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input type="email" id="email" name="email" required />
        </div>

        <div className="form-group">
          <label htmlFor="password">Mật khẩu</label>
          <input type="password" id="password" name="password" required />
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Nhập lại mật khẩu</label>
          <input type="password" id="confirmPassword" name="confirmPassword" required />
        </div>

        <div className="form-group">
          <label htmlFor="bankName">Ngân hàng</label>
          <input type="text" id="bankName" name="bankName" required />
        </div>

        <div className="form-group">
          <label htmlFor="accountNumber">Số tài khoản</label>
          <input type="text" id="accountNumber" name="accountNumber" required />
        </div>

        {error && <p className="error-message">{error}</p>}

        <button type="submit" className="btn-submit">Đăng ký</button>
      </form>
    </div>
  );
}
