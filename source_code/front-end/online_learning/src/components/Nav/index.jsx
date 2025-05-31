import { Link, NavLink } from 'react-router-dom';
import './Nav.scss';
import { FaUser } from 'react-icons/fa';
import { logout } from 'utils/AuthUtil';

function Nav() {

    const token = localStorage.getItem('token');
    const isLogin = token ? true : false;

    const handleLogout = () => {
        logout();
    }

    return (
        <>
            
            <nav className="nav fixed-top">
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-3">
                            <div className="nav__logo">
                                <img src="https://png.pngtree.com/png-vector/20240708/ourmid/pngtree-cartoon-turtle-png-image_13034273.png" alt="logo" />
                                <span>Online Learning</span>
                            </div>
                        </div>
                        <div className="col-6">
                            <ul className='nav__menu'>
                                <li><NavLink to="/" className='nav__link'>Home</NavLink></li>
                                <li><NavLink to="/contact" className='nav__link'>Contact</NavLink></li>
                                <li><NavLink to="/about" className='nav__link'>About</NavLink></li>
                                <li><NavLink to="/my-learning" className="nav__link">My Learning </NavLink></li>
                            </ul>
                        </div>
                        <div className="col-3 nav__auth">
                            {isLogin ? (
                                <>

                                    <div className="user-menu-wrapper d-flex align-items-center justify-content-center flex-column">
                                        <Link to="/infor-user"><FaUser className="user-icon" /></Link>
                                        <ul className="nav__dropdown">
                                            <li className="nav__link">Xin chào</li>
                                            <li className="nav__link" onClick={handleLogout}>Đăng xuất</li>
                                        </ul>
                                    </div>

                                    {/* <button className='btn btn-light nav__btn--signup' onClick={handleLogout}>Logout</button> */}
                                </>
                            )
                                : (
                                    <>
                                        <Link to="/login" className="link-login">
                                            Đăng nhập
                                        </Link>

                                        <Link to="/signup/student" className="link-signup">
                                            Đăng ký
                                        </Link>
                                    </>
                                )}
                        </div>

                    </div>

                </div>

            </nav>

        </>
    );
}
export default Nav;