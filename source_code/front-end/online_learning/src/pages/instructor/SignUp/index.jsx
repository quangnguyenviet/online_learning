import React, { useEffect, useState } from 'react';
import './style.scss';
import { signup } from 'utils/AuthUtil';
import Swal from 'sweetalert2';

export default function SignupInstructor() {
  const BANK_API_URL = 'https://api.vietqr.io/v2/banks';

  const [error, setError] = useState('');

  const [bankData, setBankData] = useState([]);

  // Fetch bank data from the server
  useEffect(() => {
    fetch(BANK_API_URL)
      .then((response) => response.json())
      .then((data) => {
        console.log('Bank data fetched:', data);
        setBankData(data.data);
      })
      .catch((error) => {
        console.error('Error fetching bank data:', error);
      });
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    console.log(data);

    if (data.password !== data.password2) {
      setError('Mật khẩu không khớp');
      return;
    }

    setError('');
    data.role = 'INSTRUCTOR';
    delete data.confirmPassword;

    signup(data)
      .then((response) => {
        console.log('Signup response:', response);
        if (response.status === 1000) {
          
          Swal.fire({
            title: 'Đăng ký thành công',
            text: 'Bạn đã đăng ký tài khoản giảng viên thành công. Vui lòng đăng nhập để tiếp tục.',
            icon: 'success',
            confirmButtonText: 'OK'
          });
          window.location.href = '/login';
        } else {
          Swal.fire({
            title: 'Đăng ký thất bại',
            text: response.data,
            icon: 'error',
            confirmButtonText: 'OK'
          });
        }
      })
      .catch((error) => {
        console.error('Signup error:', error);
        setError('Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại sau.');
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
          <input type="password" id="confirmPassword" name="password2" required />
        </div>

        <div className="form-group">
          <label htmlFor="bankName">Ngân hàng</label>
          {/* dropdown for bank selection */}
          <select id="bankName" name="bankName" className='form-control'  required>
            <option value="">Chọn ngân hàng</option>
            {bankData && bankData.map((bank) => (
              <option key={bank.shortName} value={bank.shortName}>
                {bank.shortName}

              </option>
            ))}
          </select>
          
        </div>

        <div className="form-group">
          <label htmlFor="accountNumber">Số tài khoản</label>
          <input type="text" id="accountNumber" name="accountNumber" required />
        </div>

        {/* Tên tài khoản */}
        <div className="form-group">
          <label htmlFor="accountName">Tên tài khoản</label>
          <input type="text" id="accountName" name="accountName" placeholder='Viết hoa không dấu' required />
        </div>

        {error && <p className="error-message">{error}</p>}

        <button type="submit" className="btn-submit">Đăng ký</button>
      </form>
    </div>
  );
}
