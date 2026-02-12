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

	// 3. Danh sách khóa học tiêu biểu
	const featuredCourses = [
		{ 
			id: 1,
			name: "React Fundamentals", 
			status: "Published",
			category: "Lập trình Web",
			students: 312, 
			revenue: "28.5M₫",
			rating: 4.8,
			lastUpdated: "2 ngày trước",
			statusColor: "success"
		},
		{ 
			id: 2,
			name: "Advanced JavaScript", 
			status: "Published",
			category: "Lập trình Web",
			students: 198, 
			revenue: "18.2M₫",
			rating: 4.6,
			lastUpdated: "5 ngày trước",
			statusColor: "success"
		},
		{ 
			id: 3,
			name: "Node.js API Design", 
			status: "Draft",
			category: "Backend",
			students: 0, 
			revenue: "0₫",
			rating: 0,
			lastUpdated: "1 giờ trước",
			statusColor: "warning"
		},
		{ 
			id: 4,
			name: "Python for Beginners", 
			status: "Published",
			category: "Lập trình",
			students: 245, 
			revenue: "22.1M₫",
			rating: 4.9,
			lastUpdated: "1 tuần trước",
			statusColor: "success"
		},
		{ 
			id: 5,
			name: "UI/UX Design Basics", 
			status: "Published",
			category: "Thiết kế",
			students: 154, 
			revenue: "16.6M₫",
			rating: 4.7,
			lastUpdated: "3 ngày trước",
			statusColor: "success"
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

	useEffect(() => {
		// call api to fetch dashboard summary
		fetchDashboardSummary();
	}, []);

	return (
		<div className={styles.dashboard}>
			{/* Header */}
			<DashboardHeader />

			{/* 1. Tổng quan nhanh */}
			<StatsGrid stats={stats} />

			{/* 3. Danh sách khóa học */}
			<CoursesSection featuredCourses={featuredCourses} />
		</div>
	);
}
