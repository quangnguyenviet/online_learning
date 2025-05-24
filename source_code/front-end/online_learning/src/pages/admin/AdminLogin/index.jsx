import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.scss";

export default function AdminLogin() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    // TODO: Gọi API xác thực ở đây
    if (email === "admin@example.com" && password === "admin123") {
      // Giả lập đăng nhập thành công
      navigate("/admin");
    } else {
      alert("Email hoặc mật khẩu không đúng");
    }
  };

  return (
    <div className="admin-login">
      <form className="login-form" onSubmit={handleLogin}>
        <h2>Admin Login</h2>
        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="admin@example.com"
          />
        </div>
        <div className="form-group">
          <label>Mật khẩu</label>
          <input
            type="password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="********"
          />
        </div>
        <button type="submit">Đăng nhập</button>
      </form>
    </div>
  );
}
