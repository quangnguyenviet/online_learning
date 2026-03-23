import { NavLink, Outlet } from "react-router-dom";
import { FaUsers, FaBook, FaMoneyBill, FaChartBar, FaSignOutAlt } from "react-icons/fa";
import "./style.scss";

export default function AdminLayout() {
  

  return (
    <div className="admin-dashboard">
      <aside className="sidebar">
        <h3 className="logo">Admin Panel</h3>
        <ul className="nav-list">
          <li><NavLink to="/admin/users"><FaUsers /> Người dùng</NavLink></li>
          <li><NavLink to="/admin/courses"><FaBook /> Khóa học</NavLink></li>
          <li><NavLink to="/admin/payments"><FaMoneyBill /> Thanh toán</NavLink></li>
          <li><NavLink to="/admin/reports"><FaChartBar /> Báo cáo</NavLink></li>
          <li><NavLink to="/logout"><FaSignOutAlt /> Đăng xuất</NavLink></li>
        </ul>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>Trang quản trị</h2>
        </header>
        <section className="admin-body">
          <Outlet />
        </section>
      </main>
    </div>
  );
}
