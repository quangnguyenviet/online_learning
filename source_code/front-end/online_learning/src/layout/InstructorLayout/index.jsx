import { Outlet, NavLink } from "react-router-dom";
import "./InstructorLayout.scss";

export default function InstructorLayout() {
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
                            Dashboard
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink
                            to="/instructor/courses"
                            className={({ isActive }) => "nav-link" + (isActive ? " active" : "")}
                        >
                            My Courses
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink
                            to="/instructor/profile"
                            className={({ isActive }) => "nav-link" + (isActive ? " active" : "")}
                        >
                            Profile
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink
                            to="/logout"
                            className={({ isActive }) => "nav-link" + (isActive ? " active" : "")}
                        >
                            Logout
                        </NavLink>
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
