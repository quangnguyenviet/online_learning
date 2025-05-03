import "components/Header/Header.scss";
import Search from "components/Search";

export default function Header() {

    return (
        <>
            <header className="header">
                <div className="container">
                    <div className="row">
                        <div className="col-7">
                            <img src="https://hcmussh.edu.vn/img/news/73534809.png?t=73534810" alt="anh" />
                        </div>
                        <div className="col-5 header__content">
                        <h1 className="mb-4">Turtle - Online hôm nay, thành công ngày mai</h1>
                        <p className="mb-4">
                            Với Turtle, bạn có thể học mọi lúc, mọi nơi chỉ với một chiếc máy tính hoặc điện thoại thông minh. Hãy cùng chúng tôi khám phá thế giới học tập trực tuyến
                        </p>   
                        
                        <Search />

                        </div>
                    </div>
                </div>

                
            </header>

        </>
    );
}