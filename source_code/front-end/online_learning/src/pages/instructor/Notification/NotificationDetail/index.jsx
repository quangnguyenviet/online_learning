import { useParams } from "react-router-dom";
import "./NotificationDetail.scss"; // import chung SCSS

const dummyNotifications = {
  1: {
    title: "Khóa học mới được đăng",
    content: "Giáo viên A đã đăng khóa học React từ cơ bản đến nâng cao. Bạn có thể xem ngay!",
  },
  2: {
    title: "Cập nhật hệ thống",
    content: "Hệ thống sẽ bảo trì lúc 12h đêm nay. Vui lòng lưu lại tiến độ học tập.",
  },
  3: {
    title: "Bạn có thông báo từ admin",
    content: "Thông tin cá nhân của bạn cần được cập nhật. Truy cập trang cá nhân để kiểm tra.",
  },
};

export default function NotificationDetail() {
  const { id } = useParams();
  const notification = dummyNotifications[id];

  if (!notification) return <p>Không tìm thấy thông báo.</p>;

  return (
    <div className="notification-detail">
      <h2>{notification.title}</h2>
      <p>{notification.content}</p>
    </div>
  );
 
}
