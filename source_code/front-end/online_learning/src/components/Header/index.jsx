import { Link, NavLink } from 'react-router-dom';
import './Header.scss';
function Header() {
    const token = localStorage.getItem('token');
    const isLogin = token ? true : false;

    const handleLogout = () => {
        fetch('http://localhost:8080/online_learning/auth/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ token: token })
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                if (data.status === 1000) {
                    localStorage.removeItem('token');
                    window.location.reload();
                }
            })

    }

    return (
        <>
            <header className="header">
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-3">
                            <div className="header__logo">
                                <img src="https://cdn.pixabay.com/photo/2024/02/16/10/42/ai-generated-8577261_640.png" alt="logo" />
                                <span>Online Learning</span>
                            </div>
                        </div>
                        <div className="col-6">
                            <ul className='header__menu'>
                                <li><NavLink to="/" className='header__link'>Home</NavLink></li>
                                <li><NavLink to="/contact" className='header__link'>Contact</NavLink></li>
                                <li><NavLink to="/about" className='header__link'>About</NavLink></li>
                                <li><NavLink to="/my-learning" className="header__link">My Learning </NavLink></li>
                            </ul>
                        </div>
                        <div className="col-3 header__auth">
                            {isLogin ? (
                                <>
                                    <button className='btn btn-primary header__btn--login'>
                                        <Link to="/infor-user">Infor User</Link>
                                    </button>
                                    <button className='btn btn-light header__btn--signup' onClick={handleLogout}>Logout</button>
                                </>
                            ) 
                            : (
                                <>
                                    <button className='btn btn-primary header__btn--login'>
                                        <Link to="/login">Login</Link>
                                    </button>
                                    <button className='btn btn-light header__btn--signup' >
                                        <Link to="/signup">Signup</Link>
                                    </button>
                                </>
                            )}
                        </div>
                        
                    </div>

                </div>

            </header>
        </>
    );
}
export default Header;