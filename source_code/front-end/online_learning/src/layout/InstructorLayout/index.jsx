import { Outlet, NavLink } from "react-router-dom";
import { FaChalkboardTeacher, FaBook, FaUser, FaSignOutAlt, FaPlus } from "react-icons/fa";
import "./InstructorLayout.scss";
import { logout } from "utils/AuthUtil";

export default function InstructorLayout() {

    const handleLogout = () => {
        logout();
    }

    return (
        <div className="dashboard d-flex">
            {/* Sidebar bên trái */}
            <div className="sidebar p-3">
                <h4 className="text-center mb-4">Instructor</h4>
                <ul className="nav flex-column">
                    <li className="nav-item">
                        <NavLink
                            to="/instructor/dashboard"
                            className={({ isActive }) => "nav-link" + (isActive ? " active" : "")}
                        >
                            <FaChalkboardTeacher className="me-2" />
                            Dashboard
                        </NavLink>
                    </li>

                    {/* Dropdown cho My Courses */}
                    <li className="nav-item dropdown">
                        <a
                            href="#"
                            className="nav-link dropdown-toggle"
                            data-bs-toggle="dropdown"
                        >
                            <FaBook className="me-2" />
                            My Courses
                        </a>
                        <ul className="dropdown-menu dropdown-menu-dark">
                            <li>
                                <NavLink to="/instructor/courses" className="dropdown-item">
                                    Danh sách khóa học
                                </NavLink>
                            </li>
                            <li>
                                <NavLink to="/instructor/courses/add-new" className="dropdown-item">
                                    Thêm khóa học
                                </NavLink>
                            </li>
                        </ul>
                    </li>

                    <li className="nav-item">
                        <NavLink
                            to="/instructor/profile"
                            className={({ isActive }) => "nav-link" + (isActive ? " active" : "")}
                        >
                            <FaUser className="me-2" />
                            Profile
                        </NavLink>
                    </li>
                    <li className="nav-item" onClick = {handleLogout} >
                        <a onClick={handleLogout}
                            
                        className="nav-link "
                        >
                            <FaSignOutAlt className="me-2" />
                            Logout
                        </a>
                    </li>
                </ul>
            </div>

            {/* Nội dung chính */}
            <div className="main-content p-4 flex-grow-1">
                <Outlet />
            </div>
        </div>
    );
}
