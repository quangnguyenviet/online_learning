import styles from "./Dashboard.module.scss";

export default function InstructorDashboard() {
	const stats = [
		{ label: "Total Students", value: "1,248", trend: "+4.2%" },
		{ label: "Active Courses", value: "12", trend: "+1" },
		{ label: "Monthly Earnings", value: "$3,540", trend: "+$180" },
		{ label: "Avg. Rating", value: "4.7", trend: "⭐" },
	];

	const courses = [
		{ title: "React Fundamentals", students: 312, progress: 78, status: "Active" },
		{ title: "Advanced JavaScript", students: 198, progress: 62, status: "Updating" },
		{ title: "UI/UX Basics", students: 154, progress: 91, status: "Active" },
		{ title: "Node.js API Design", students: 86, progress: 40, status: "Draft" },
	];

	const lessons = [
		{ when: "Jan 12, 09:00", course: "React Fundamentals", topic: "Hooks Deep Dive" },
		{ when: "Jan 13, 15:30", course: "Node.js API Design", topic: "Auth & Rate Limiting" },
		{ when: "Jan 15, 10:15", course: "Advanced JavaScript", topic: "Async Patterns" },
	];

	const performance = [
		{ label: "Engagement", value: 76 },
		{ label: "Completion", value: 64 },
		{ label: "Retention", value: 71 },
		{ label: "Satisfaction", value: 88 },
	];

	return (
		<div className={styles.dashboard}>
			<header className={styles.header}>
				<div className={styles.headerText}>
					<h1 className={styles.title}>Instructor Dashboard</h1>
					<p className={styles.subtitle}>Welcome back, Alex. Here's your overview.</p>
				</div>
				<div className={styles.actions}>
					<button className={styles.buttonPrimary}>Create Course</button>
					<button className={styles.buttonSecondary}>View Students</button>
				</div>
			</header>

			<section className={styles.statsGrid} aria-label="Key stats">
				{stats.map((s) => (
					<div key={s.label} className={styles.card}>
						<div className={styles.cardValue}>{s.value}</div>
						<div className={styles.cardLabel}>{s.label}</div>
						<div className={styles.cardTrend}>{s.trend}</div>
					</div>
				))}
			</section>

			<section className={styles.mainGrid}>
				<div className={styles.section}>
					<div className={styles.sectionHeader}>
						<h3>Recent Courses</h3>
						<a href="#" className={styles.sectionLink}>Manage</a>
					</div>
					<div className={styles.courseList}>
						{courses.map((c) => (
							<div key={c.title} className={styles.courseItem}>
								<div className={styles.courseInfo}>
									<div className={styles.courseTitle}>{c.title}</div>
									<div className={styles.courseMeta}>{c.students} students • {c.status}</div>
								</div>
								<div className={styles.progress}>
									<div className={styles.progressTrack}>
										<div
											className={styles.progressBar}
											style={{ width: `${c.progress}%` }}
										/>
									</div>
									<div className={styles.progressPct}>{c.progress}%</div>
								</div>
							</div>
						))}
					</div>
				</div>

				<div className={styles.section}>
					<div className={styles.sectionHeader}>
						<h3>Upcoming Lessons</h3>
						<a href="#" className={styles.sectionLink}>View calendar</a>
					</div>
					<div className={styles.lessonList}>
						{lessons.map((l) => (
							<div key={l.when + l.course} className={styles.lessonItem}>
								<div className={styles.lessonDate}>{l.when}</div>
								<div className={styles.lessonInfo}>
									<div className={styles.lessonCourse}>{l.course}</div>
									<div className={styles.lessonTopic}>{l.topic}</div>
								</div>
							</div>
						))}
					</div>
				</div>
			</section>

			<section className={styles.secondaryGrid}>
				<div className={styles.section}>
					<div className={styles.sectionHeader}>
						<h3>Performance Overview</h3>
					</div>
					<div className={styles.performance}>
						{performance.map((p) => (
							<div key={p.label} className={styles.perfItem}>
								<span className={styles.perfLabel}>{p.label}</span>
								<div className={styles.perfTrack}>
									<div
										className={styles.perfBar}
										style={{ width: `${p.value}%` }}
									/>
								</div>
								<span className={styles.perfValue}>{p.value}%</span>
							</div>
						))}
					</div>
				</div>

				<div className={styles.section}>
					<div className={styles.sectionHeader}>
						<h3>Announcements</h3>
					</div>
					<ul className={styles.announcements}>
						<li>
							Course review period starts next week. Prepare updates.
						</li>
						<li>
							Payment cycle closes on Jan 28.
						</li>
						<li>
							New community Q&A session on Friday.
						</li>
					</ul>
				</div>
			</section>
		</div>
	);
}
