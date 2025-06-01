import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.scss";
import { authenticate } from "utils/AuthUtil";
import { setData } from 'service/localStorageService';
import Swal from "sweetalert2";
export default function AdminLogin() {
      const URL_AUTH = 'http://localhost:8080/online_learning/auth/login';

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());

    authenticate(URL_AUTH, 'POST', data)
      .then((data) => {
        if (data.status === 1000) {
          setData('token', data.data.token);
          setData('role', "ADMIN");
          setData('id', data.data.id);
          navigate('/admin');
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
    <div className="admin-login">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Admin Login</h2>
        <div className="form-group">
          <label>Tên đăng nhập</label>
          <input
            type="text"
            required
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            name="username"
            placeholder="admin"
          />
        </div>
        <div className="form-group">
          <label>Mật khẩu</label>
          <input
            type="password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            name="password"
            placeholder="********"
          />
        </div>
        <button type="submit">Đăng nhập</button>
      </form>
    </div>
  );
}
