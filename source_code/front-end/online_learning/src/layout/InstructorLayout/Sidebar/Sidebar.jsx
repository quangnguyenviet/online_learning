import { NavLink } from "react-router-dom";
import { FaChalkboardTeacher, FaBook, FaUser, FaSignOutAlt, FaBell, FaChevronDown, FaUserGraduate } from "react-icons/fa";
import { logout } from "utils/AuthUtil";
import { useState } from "react";
import styles from "./Sidebar.module.scss";

export default function Sidebar() {
    const [showCoursesDropdown, setShowCoursesDropdown] = useState(false);

    const handleLogout = () => {
        logout();
    };

    return (
        <div className={styles.sidebar}>
            <div className={styles.sidebar__header}>
                <h4>EduPanel</h4>
            </div>
            
            <ul className={styles.sidebar__nav}>
                <li className={styles['sidebar__nav-item']}>
                    <NavLink
                        to="/instructor/dashboard"
                        className={({ isActive }) => 
                            `${styles['sidebar__nav-link']}${isActive ? ' ' + styles.active : ''}`
                        }
                    >
                        <FaChalkboardTeacher />
                        <span>Dashboard</span>
                    </NavLink>
                </li>

                <li className={`${styles['sidebar__nav-item']} ${styles.sidebar__dropdown}`}>
                    <div
                        className={`${styles['sidebar__nav-link']} ${styles['dropdown-toggle']} ${showCoursesDropdown ? styles.open : ''}`}
                        onClick={() => setShowCoursesDropdown(!showCoursesDropdown)}
                    >
                        <FaBook />
                        <span>My Courses</span>
                        <FaChevronDown className={styles.chevron} />
                    </div>
                    
                    <ul className={`${styles['sidebar__dropdown-menu']} ${showCoursesDropdown ? styles.show : ''}`}>
                        <li>
                            <NavLink 
                                to="/instructor/courses" 
                                end 
                                className={({ isActive }) => 
                                    `${styles['dropdown-item']}${isActive ? ' ' + styles.active : ''}`
                                }
                            >
                                Danh sách khóa học
                            </NavLink>
                        </li>
                        <li>
                            <NavLink 
                                to="/instructor/courses/add-new" 
                                className={({ isActive }) => 
                                    `${styles['dropdown-item']}${isActive ? ' ' + styles.active : ''}`
                                }
                            >
                                Thêm khóa học
                            </NavLink>
                        </li>
                    </ul>
                </li>

                <li className={styles['sidebar__nav-item']}>
                    <NavLink
                        to="/instructor/profile"
                        className={({ isActive }) => 
                            `${styles['sidebar__nav-link']}${isActive ? ' ' + styles.active : ''}`
                        }
                    >
                        <FaUser />
                        <span>Profile</span>
                    </NavLink>
                </li>

                <li className={styles['sidebar__nav-item']}>
                    <NavLink
                        to="/instructor/notifications"
                        className={({ isActive }) => 
                            `${styles['sidebar__nav-link']}${isActive ? ' ' + styles.active : ''}`
                        }
                    >
                        <FaBell />
                        <span>Thông báo</span>
                    </NavLink>
                </li>

                <li className={styles['sidebar__nav-item']}>
                    <NavLink
                        to="/"
                        end
                        className={({ isActive }) => 
                            `${styles['sidebar__nav-link']}${isActive ? ' ' + styles.active : ''}`
                        }
                    >
                        <FaUserGraduate />
                        <span>Trang học sinh</span>
                    </NavLink>
                </li>

                <li className={styles['sidebar__nav-item']} onClick={handleLogout} style={{ marginTop: 'auto' }}>
                    <a className={styles['sidebar__nav-link']}>
                        <FaSignOutAlt />
                        <span>Logout</span>
                    </a>
                </li>
            </ul>
        </div>
    );
}