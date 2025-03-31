import { NavLink } from 'react-router-dom';
import './Header.scss';
function Header() {
    let isLogin = false;

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
                            </ul>
                        </div>
                        <div className="col-3 header__auth">
                            {isLogin ? (
                                <div>
                                    account
                                </div>
                            ) 
                            : (
                                <>
                                    <button className='btn btn-primary header__btn--login'>login</button>
                                    <button className='btn btn-light header__btn--signup' >signup</button>
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