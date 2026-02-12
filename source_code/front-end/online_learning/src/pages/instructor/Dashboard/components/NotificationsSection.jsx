import styles from "../Dashboard.module.scss";
import { MdWarning } from "react-icons/md";

export default function NotificationsSection({ notifications }) {
	return (
		<section className={styles.notificationSection}>
			<div className={styles.sectionHeader}>
				<h2>
					<MdWarning size={24} style={{ marginRight: "8px", verticalAlign: "middle" }} />
					Nhắc việc
				</h2>
			</div>
			<div className={styles.notificationList}>
				{notifications.map((notif, idx) => (
					<div key={idx} className={`${styles.notificationItem} ${styles[notif.type]}`}>
						<span className={styles.notifIcon}>{notif.icon}</span>
						<div className={styles.notifContent}>
							<p>{notif.message}</p>
							<a href="#" className={styles.notifAction}>{notif.action} →</a>
						</div>
					</div>
				))}
			</div>
		</section>
	);
}
