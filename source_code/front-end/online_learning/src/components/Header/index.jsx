import "components/Header/Header.scss";
import Search from "components/Search";

export default function Header() {
    return (
        <header className="header">
            <div className="header__container">
                <div className="header__img-wrap">
                    <img src="https://hcmussh.edu.vn/img/news/73534809.png?t=73534810" alt="Banner" />
                </div>
                <div className="header__content">
                    <h1>Turtle - Online hôm nay, thành công ngày mai</h1>
                    <p>
                        Với Turtle, bạn có thể học mọi lúc, mọi nơi chỉ với một chiếc máy tính hoặc điện thoại thông minh.<br />
                        Hãy cùng chúng tôi khám phá thế giới học tập trực tuyến!
                    </p>
                    <Search />
                </div>
            </div>
        </header>
    );
}