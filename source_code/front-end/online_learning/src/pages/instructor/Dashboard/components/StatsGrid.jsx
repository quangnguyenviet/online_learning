import styles from "../Dashboard.module.scss";

export default function StatsGrid({ stats }) {
	return (
		<section className={styles.statsGrid}>
			{stats.map((s, idx) => (
				<div key={idx} className={styles.statCard}>
					<div className={styles.statIcon} style={{ color: s.color }}>
						{s.icon}
					</div>
					<div className={styles.statContent}>
						<div className={styles.statLabel}>{s.label}</div>
						<div className={styles.statValue}>{s.value}</div>
						<div className={styles.statSubText}>{s.subText}</div>
					</div>
				</div>
			))}
		</section>
	);
}
