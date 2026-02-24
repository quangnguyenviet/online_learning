import { Link, NavLink } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { logout } from 'utils/AuthUtil';
import { useState, useRef, useEffect } from 'react';
import { isLoggedIn, isInstructor, isAdmin } from 'service/Guard';
import styles from './Nav.module.scss';

function Nav() {
    const isLogin = isLoggedIn();
    const [showDropdown, setShowDropdown] = useState(false);
    const dropdownRef = useRef();

    const handleLogout = () => {
        logout();
    };
    const handleClickUserIcon = (e) => {
        e.preventDefault();
        setShowDropdown((v) => !v);
    }

    // Đóng dropdown khi click ra ngoài
    useEffect(() => {
        function handleClickOutside(event) {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                setShowDropdown(false);
            }
        }
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    return (
        <nav className={styles['navbar']}>
            <div className={styles['navbar__container']}>
                <Link to="/" className={styles['navbar__logo']}>
                    <img src="https://png.pngtree.com/png-vector/20240708/ourmid/pngtree-cartoon-turtle-png-image_13034273.png" alt="logo" />
                    <span>Online Learning</span>
                </Link>
                <ul className={styles['navbar__menu']}>
                    <li><NavLink to="/" className={styles['navbar__link']}>Trang chủ</NavLink></li>
                    <li><NavLink to="/contact" className={styles['navbar__link']}>Liên hệ</NavLink></li>
                    <li><NavLink to="/about" className={styles['navbar__link']}>Giới thiệu</NavLink></li>
                    <li><NavLink to="/my-learning" className={styles['navbar__link']}>Khóa học của tôi</NavLink></li>

                </ul>
                <div className={styles['navbar__auth']}>
                    {isLogin ? (
                        <div className={styles['user-dropdown']} ref={dropdownRef}>
                            <div
                                onClick={handleClickUserIcon}>
                                <FaUser className={styles['user-avatar']} />
                            </div>
                            <ul className={`${styles['user-dropdown-menu']}${showDropdown ? " " + styles['active'] : ""}`}>

                                <li >
                                    <Link className={styles['user-dropdown-item']} to="/infor-user">Thông tin cá nhân</Link>
                                </li>

                                {/* for instructor only */}
                                {isInstructor() && (
                                    <li><Link to="/instructor/dashboard" className={styles['user-dropdown-item']}>Chuyển sang Giáo viên</Link></li>
                                )}
                                {/* for admin only */}
                                {isAdmin() && (
                                    <li><Link to="/admin" className={styles['user-dropdown-item']}>Chuyển sang Quản trị viên</Link></li>
                                )}

                                <li onClick={handleLogout}>
                                    <Link className={styles['user-dropdown-item']} to="/logout"> Đăng xuất</Link>
                                </li>

                            </ul>
                        </div>
                    ) : (
                        <>
                            <Link to="/login" className={styles['navbar__login-button']}>
                                Đăng nhập
                            </Link>
                            <Link to="/signup/student" className={styles['navbar__signup-button']}>
                                Đăng ký
                            </Link>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
}
export default Nav;