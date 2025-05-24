import { useState } from 'react';
import './style.scss';

export default function Signup() {
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
    delete data.confirmPassword; // Xóa field này trước khi gửi

    fetch('http://localhost:8080/online_learning/users', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status === 1000) {
          alert('Đăng ký thành công!');
        } else {
          alert('Đăng ký thất bại: ' + data.message);
        }
      });
  };

  return (
    <div className="signup-container">
      <form className="signup-form" onSubmit={handleSubmit}>
        <h2>Tạo tài khoản mới</h2>
        <p>Vui lòng nhập thông tin bên dưới để đăng ký</p>

        <div className="form-group">
          <label htmlFor="username">Tên đăng nhập</label>
          <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập" required />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Nhập email" required />
        </div>

        <div className="form-group">
          <label htmlFor="password">Mật khẩu</label>
          <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required />
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Nhập lại mật khẩu</label>
          <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nhập lại mật khẩu" required />
        </div>

        {error && <p className="error-message">{error}</p>}

        <button type="submit" className="btn-submit">Đăng ký</button>
      </form>
    </div>
  );
}
