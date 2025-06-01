import { Link, NavLink } from 'react-router-dom';
import './Nav.scss';
import { FaUser } from 'react-icons/fa';
import { logout } from 'utils/AuthUtil';
import { useState, useRef, useEffect } from 'react';

function Nav() {
    const token = localStorage.getItem('token');
    const isLogin = !!token;
    const [showDropdown, setShowDropdown] = useState(false);
    const dropdownRef = useRef();

    const handleLogout = () => {
        logout();
    };

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
        <nav className="nav-bar">
            <div className="nav-bar__container">
                <div className="nav-bar__logo">
                    <img src="https://png.pngtree.com/png-vector/20240708/ourmid/pngtree-cartoon-turtle-png-image_13034273.png" alt="logo" />
                    <span>Online Learning</span>
                </div>
                <ul className="nav-bar__menu">
                    <li><NavLink to="/" className="nav-bar__link">Home</NavLink></li>
                    <li><NavLink to="/contact" className="nav-bar__link">Contact</NavLink></li>
                    <li><NavLink to="/about" className="nav-bar__link">About</NavLink></li>
                    <li><NavLink to="/my-learning" className="nav-bar__link">My Learning</NavLink></li>
                </ul>
                <div className="nav-bar__auth">
                    {isLogin ? (
                        <div className="user-menu-wrapper" ref={dropdownRef}>
                            <Link to="/infor-user" tabIndex={0}
                                onClick={e => {
                                    e.preventDefault();
                                    setShowDropdown((v) => !v);
                                }}>
                                <FaUser className="user-icon" />
                            </Link>
                            <ul className={`nav-bar__dropdown${showDropdown ? " show" : ""}`}>
                                <li className="nav-bar__dropdown-item">Xin chào</li>
                                <li className="nav-bar__dropdown-item" onClick={handleLogout}>Đăng xuất</li>
                            </ul>
                        </div>
                    ) : (
                        <>
                            <Link to="/login" className="nav-bar__login-btn">
                                Đăng nhập
                            </Link>
                            <Link to="/signup/student" className="nav-bar__signup-btn">
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