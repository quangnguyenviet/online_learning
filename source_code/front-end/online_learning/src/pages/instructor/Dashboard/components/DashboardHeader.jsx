import styles from "../Dashboard.module.scss";
import { MdShowChart } from "react-icons/md";

export default function DashboardHeader() {
	return (
		<header className={styles.header}>
			<div className={styles.headerText}>
				<h1 className={styles.title}>Bảng Điều Khiển Giảng Viên</h1>
				<p className={styles.subtitle}>Xin chào! Đây là tổng quan của bạn hôm nay.</p>
			</div>
			<div className={styles.actions}>
				<button className={styles.buttonPrimary}>+ Tạo Khóa Học</button>
				<button className={styles.buttonSecondary}>
					<MdShowChart size={18} style={{ marginRight: "6px", verticalAlign: "middle" }} />
					Báo Cáo
				</button>
			</div>
		</header>
	);
}
