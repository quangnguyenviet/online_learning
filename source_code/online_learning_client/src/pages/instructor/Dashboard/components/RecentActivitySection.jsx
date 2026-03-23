import styles from "../Dashboard.module.scss";
import { MdInfoOutline } from "react-icons/md";

export default function RecentActivitySection({ recentActivities }) {
	return (
		<section className={styles.activitySection}>
			<div className={styles.sectionHeader}>
				<h2>
					<MdInfoOutline size={24} style={{ marginRight: "8px", verticalAlign: "middle" }} />
					Hoạt động gần đây
				</h2>
			</div>
			<div className={styles.activityList}>
				{recentActivities.map((activity, idx) => (
					<div key={idx} className={styles.activityItem}>
						<span className={styles.activityIcon}>{activity.icon}</span>
						<div className={styles.activityContent}>
							<p>
								{activity.text}{" "}
								{activity.highlight && (
									<strong>{activity.highlight}</strong>
								)}
								{activity.text2 && ` ${activity.text2}`}
							</p>
							<span className={styles.activityTime}>{activity.time}</span>
						</div>
					</div>
				))}
			</div>
		</section>
	);
}
