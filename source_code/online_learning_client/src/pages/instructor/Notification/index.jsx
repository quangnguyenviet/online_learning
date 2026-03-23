import { Link, Outlet } from "react-router-dom";
import "./Notification.scss"; // import SCSS
import { useParams } from "react-router-dom";

const dummyNotifications = [
    { id: 1, title: "Khóa học mới được đăng", excerpt: "Giáo viên A đã đăng khóa học React mới..." },
    { id: 2, title: "Cập nhật hệ thống", excerpt: "Hệ thống sẽ bảo trì vào lúc 12h đêm nay..." },
    { id: 3, title: "Bạn có thông báo từ admin", excerpt: "Vui lòng kiểm tra lại thông tin cá nhân..." },
];

export default function Notification() {
    const { id } = useParams();

    return (
        <>
            {!id &&
                <div className="notification-container">
                    <h2>Thông báo</h2>
                    <ul className="notification-list">
                        {dummyNotifications.map((notification) => (
                            <li key={notification.id} className="notification-item">
                                <Link to={`/instructor/notifications/${notification.id}`} className="notification-link">
                                    <strong>{notification.title}</strong>
                                    <p>{notification.excerpt}</p>
                                </Link>
                            </li>
                        ))}
                    </ul>

                </div>}

            <Outlet />
        </>


    );
}
