import './Header.scss';
function Header() {

    return(
    <>
        <header>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="header">
                            <div class="header__logo">
                                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVJubXDaT-Vhu8oKFvEKsEipo58f-nWO2nHg&s" alt="logo" />
                                    <p>Online Learning</p>
                            </div>
                            <div class="header__search">
                                <input type="text" />
                            </div>
                            <div class="header__btn">
                                <button class="header__btn--signUp">Sign up</button>
                                <button class="header__btn--login">Login</button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </header>
    </>
    );
}
export default Header;