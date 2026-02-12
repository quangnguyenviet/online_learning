import styles from "../Dashboard.module.scss";
import { MdSchool, MdPeople, MdStar, MdVisibility, MdEdit, MdPublish, MdDelete } from "react-icons/md";

export default function CoursesSection({ featuredCourses }) {
	return (
		<section className={styles.coursesSection}>
			<div className={styles.sectionHeader}>
				<h2>
					<MdSchool size={24} style={{ marginRight: "8px", verticalAlign: "middle" }} />
					Khóa học tiêu biểu
				</h2>
				<a href="#all-courses" className={styles.viewAllLink}>
					Xem tất cả →
				</a>
			</div>
			<div className={styles.coursesList}>
				<table className={styles.coursesTable}>
					<thead>
						<tr>
							<th>Tên khóa học</th>
							<th>Danh mục</th>
							<th>Trạng thái</th>
							<th>Học sinh</th>
							<th>Đánh giá</th>
							<th>Doanh thu</th>
							<th>Cập nhật</th>
							<th>Thao tác</th>
						</tr>
					</thead>
					<tbody>
						{featuredCourses.map((course) => (
							<tr key={course.id}>
								<td className={styles.courseName}>
									<div className={styles.courseNameWrapper}>
										<MdSchool size={18} style={{ marginRight: "8px", color: "#6B7280" }} />
										{course.name}
									</div>
								</td>
								<td className={styles.courseCategory}>{course.category}</td>
								<td>
									<span className={`${styles.statusBadge} ${styles[course.statusColor]}`}>
										{course.status === "Published" ? "Đã xuất bản" : "Nháp"}
									</span>
								</td>
								<td>
									<div className={styles.studentCount}>
										<MdPeople size={16} style={{ marginRight: "4px" }} />
										{course.students}
									</div>
								</td>
								<td>
									{course.rating > 0 ? (
										<div className={styles.rating}>
											<MdStar size={16} style={{ color: "#F59E0B", marginRight: "4px" }} />
											{course.rating}
										</div>
									) : (
										<span className={styles.noRating}>—</span>
									)}
								</td>
								<td className={styles.revenue}>{course.revenue}</td>
								<td className={styles.lastUpdated}>{course.lastUpdated}</td>
								<td className={styles.courseActions}>
									<button className={styles.actionBtn} title="Xem">
										<MdVisibility size={18} />
									</button>
									<button className={styles.actionBtn} title="Sửa">
										<MdEdit size={18} />
									</button>
									{course.status === "Draft" && (
										<button className={styles.actionBtn} title="Xuất bản">
											<MdPublish size={18} />
										</button>
									)}
									<button className={styles.actionBtnDanger} title="Xóa">
										<MdDelete size={18} />
									</button>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</div>
		</section>
	);
}
