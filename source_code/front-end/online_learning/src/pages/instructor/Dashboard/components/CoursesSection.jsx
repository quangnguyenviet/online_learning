import styles from "./CoursesSection.module.scss";
import { MdSchool, MdPeople, MdStar, MdVisibility, MdEdit, MdPublish, MdDelete, MdTimer } from "react-icons/md";

export default function CoursesSection({ coursesData, onPageChange }) {
	const { courses, totalPages, totalElements, currentPage, pageSize } = coursesData;

	// Format duration from seconds to readable format
	const formatDuration = (seconds) => {
		if (!seconds || seconds === 0) return "—";
		const hours = Math.floor(seconds / 3600);
		const minutes = Math.floor((seconds % 3600) / 60);
		if (hours > 0) {
			return `${hours}h ${minutes}m`;
		}
		return `${minutes}m`;
	};

	// Format currency
	const formatCurrency = (amount) => {
		if (!amount || amount === 0) return "0₫";
		return `${amount.toLocaleString('vi-VN')}₫`;
	};

	const handlePageChange = (newPage) => {
		if (newPage >= 0 && newPage < totalPages) {
			onPageChange(newPage, pageSize);
		}
	};

	return (
		<section className={styles.coursesSection}>
			<div className={styles.sectionHeader}>
				<h2>
					<MdSchool size={24} style={{ marginRight: "8px", verticalAlign: "middle" }} />
					Khóa học tiêu biểu
				</h2>
				<span className={styles.totalCourses}>
					Tổng: {totalElements} khóa học
				</span>
			</div>
			<div className={styles.coursesList}>
				<table className={styles.coursesTable}>
					<thead>
						<tr>
							<th>Tên khóa học</th>
							<th>Số học sinh</th>
							<th>Doanh thu</th>
							<th>Thời lượng</th>
							<th>Thao tác</th>
						</tr>
					</thead>
					<tbody>
						{courses.length > 0 ? (
							courses.map((course) => (
								<tr key={course.id}>
									<td className={styles.courseName}>
										<div className={styles.courseNameWrapper}>
											<MdSchool size={18} style={{ marginRight: "8px", color: "#6B7280" }} />
											{course.title}
										</div>
									</td>
									<td>
										<div className={styles.studentCount}>
											<MdPeople size={16} style={{ marginRight: "4px" }} />
											{course.totalRegistrations || 0}
										</div>
									</td>
									<td className={styles.revenue}>{formatCurrency(course.totalEarnings)}</td>
									<td>
										<div className={styles.duration}>
											<MdTimer size={16} style={{ marginRight: "4px", color: "#6B7280" }} />
											{formatDuration(course.totalDurationInSeconds)}
										</div>
									</td>
									<td className={styles.courseActions}>
										<button className={styles.actionBtn} title="Xem">
											<MdVisibility size={18} />
										</button>
										<button className={styles.actionBtn} title="Sửa">
											<MdEdit size={18} />
										</button>
										<button className={styles.actionBtnDanger} title="Xóa">
											<MdDelete size={18} />
										</button>
									</td>
								</tr>
							))
						) : (
							<tr>
								<td colSpan="5" className={styles.noData}>
									Không có dữ liệu khóa học
								</td>
							</tr>
						)}
					</tbody>
				</table>
			</div>

			{/* Pagination */}
			{totalPages > 1 && (
				<div className={styles.pagination}>
					<button 
						className={styles.pageBtn}
						onClick={() => handlePageChange(currentPage - 1)}
						disabled={currentPage === 0}
					>
						Trước
					</button>
					<span className={styles.pageInfo}>
						Trang {currentPage + 1} / {totalPages}
					</span>
					<button 
						className={styles.pageBtn}
						onClick={() => handlePageChange(currentPage + 1)}
						disabled={currentPage === totalPages - 1}
					>
						Sau
					</button>
				</div>
			)}
		</section>
	);
}
