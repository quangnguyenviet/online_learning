import { useEffect, useState } from "react";
import styles from "./Dashboard.module.scss";
import InstructorDashboardApi from "service/apis/InstructorDashboardApi";
import { MdSchool, MdPeople, MdPlayArrow, MdAttachMoney, MdStar } from "react-icons/md";
import DashboardHeader from "./components/DashboardHeader";
import StatsGrid from "./components/StatsGrid";
import CoursesSection from "./components/CoursesSection";

export default function InstructorDashboard() {
	const [dashboardData, setDashboardData] = useState({
		totalCourses: 0,
		publishedCourses: 0,
		totalStudents: 0,
		totalVideos: 0,
		totalEarnings: 0,
		averageRating: 0.0
	});

	const [coursesData, setCoursesData] = useState({
		courses: [],
		totalPages: 0,
		totalElements: 0,
		currentPage: 0,
		pageSize: 5
	});

	// dashboard summary data
	const stats = [
		{ 
			icon: <MdSchool size={40} />, 
			label: "Số khóa học", 
			value: dashboardData.totalCourses.toString(), 
			subText: `${dashboardData.publishedCourses} đã xuất bản`,
			color: "#3B82F6"
		},
		{ 
			icon: <MdPeople size={40} />, 
			label: "Tổng học sinh", 
			value: dashboardData.totalStudents.toLocaleString(), 
			subText: "Tổng số học sinh",
			color: "#10B981"
		},
		{ 
			icon: <MdPlayArrow size={40} />, 
			label: "Tổng video", 
			value: dashboardData.totalVideos.toString(), 
			subText: "Video đã tải lên",
			color: "#8B5CF6"
		},
		{ 
			icon: <MdAttachMoney size={40} />, 
			label: "Doanh thu", 
			value: `${dashboardData.totalEarnings.toLocaleString('vi-VN')}₫`, 
			subText: "Tổng doanh thu",
			color: "#F59E0B"
		},
		{ 
			icon: <MdStar size={40} />, 
			label: "Đánh giá TB", 
			value: dashboardData.averageRating.toFixed(1), 
			subText: "Điểm trung bình",
			color: "#EF4444"
		},
	];

	const fetchDashboardSummary = async () => {
		try {
			const response = await InstructorDashboardApi.getInstructorDashboardSummary();
			if (response.status === 1000 && response.data) {
				const data = response.data;
				console.log("Dashboard Summary Data:", data);
				setDashboardData(data);
			}
		} catch (error) {
			console.error("Error fetching dashboard summary:", error);
		}
	};

	const fetchCoursesStats = async (page = 0, size = 1) => {
		try {
			const response = await InstructorDashboardApi.getCoursesStats(page, size);
			if (response.status === 1000 && response.data) {
				const data = response.data;
				console.log("Courses Stats Data:", data);
				setCoursesData({
					courses: data.content || [],
					totalPages: data.totalPages || 0,
					totalElements: data.totalElements || 0,
					currentPage: data.number || 0,
					pageSize: data.size || 5
				});
			}
		} catch (error) {
			console.error("Error fetching courses stats:", error);
		}
	};

	useEffect(() => {
		// call api to fetch dashboard summary
		fetchDashboardSummary();
		// call api to fetch courses stats
		fetchCoursesStats();
	}, []);

	return (
		<div className={styles.dashboard}>
			{/* Header */}
			<DashboardHeader />

			{/* 1. Tổng quan nhanh */}
			<StatsGrid stats={stats} />

			{/* 3. Danh sách khóa học */}
			<CoursesSection 
				coursesData={coursesData} 
				onPageChange={fetchCoursesStats}
			/>
		</div>
	);
}
