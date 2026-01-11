import Search from "components/Search";
import styles from "./Header.module.scss";

export default function Header() {
    return (
        <header className={styles.header}>
            <div className={styles['header__container']}>
                <div className={styles['header__img-wrap']}>
                    <img src="https://hcmussh.edu.vn/img/news/73534809.png?t=73534810" alt="Banner" />
                </div>
                <div className={styles['header__content']}>
                    <h1>Online hôm nay, thành công ngày mai</h1>
                    <p>
                        Với Online-learning, bạn có thể học mọi lúc, mọi nơi chỉ với một chiếc máy tính hoặc điện thoại thông minh.<br />
                        Hãy cùng chúng tôi khám phá thế giới học tập trực tuyến!
                    </p>
                    <Search />
                </div>
            </div>
        </header>
    );
}