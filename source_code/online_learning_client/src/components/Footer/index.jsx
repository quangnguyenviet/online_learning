import "./Footer.scss";

export default function Footer() {
    return (
        <footer className="footer">
            <div className="footer__content">
                <div className="footer__section">
                    <h2 className="footer__brand">Turtle Online Learning</h2>
                    <p className="footer__desc">
                        Empowering learners everywhere with quality education.<br />
                        Học tập mọi lúc, mọi nơi cùng Turtle!
                    </p>
                </div>
                <div className="footer__section">
                    <h3>Liên kết</h3>
                    <ul>
                        <li><a href="/courses">Khóa học</a></li>
                        <li><a href="/about">Về chúng tôi</a></li>
                        <li><a href="/contact">Liên hệ</a></li>
                    </ul>
                </div>
                <div className="footer__section">
                    <h3>Kết nối</h3>
                    <ul className="footer__social">
                        <li><a href="#"><i className="fab fa-facebook-f"></i> Facebook</a></li>
                        <li><a href="#"><i className="fab fa-twitter"></i> Twitter</a></li>
                        <li><a href="#"><i className="fab fa-linkedin-in"></i> LinkedIn</a></li>
                    </ul>
                </div>
            </div>
            <div className="footer__bottom">
                &copy; {new Date().getFullYear()} Turtle Online Learning. All rights reserved.
            </div>
        </footer>
    );
}
