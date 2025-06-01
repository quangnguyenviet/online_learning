import React, { useState, useEffect } from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
} from "recharts";
import "./style.scss";
import CourseStatisticsTable from "./CourseStatisticsTable";
import { getCourseStatistics } from "utils/InstructorUtil/StatisticUtil";
import { getInstructorStattic } from "utils/InstructorUtil/InstructorStatisticUtil";

export default function InstructorDashboard() {
  const [stats, setStats] = useState("");
  const [instructorStats, setInstructorStats] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCourseStatistics()
      .then((result) => {
        if (result) setStats(result);
      })
      .catch((error) => {
        console.error("Error fetching course statistics:", error);
      });

    getInstructorStattic()
      .then((result) => {
        if (result) {
          setInstructorStats(result.data);
          setLoading(false);
        }
      });
  }, []);

  return (
    <section className="instructor-dashboard">
      {loading ? (
        <div className="instructor-dashboard__loading">Đang tải...</div>
      ) : (
        <>
          <h2 className="instructor-dashboard__title">
            <i className="fas fa-chalkboard-teacher"></i> Bảng điều khiển Giảng viên
          </h2>

          {/* Tổng quan */}
          <div className="dashboard-summary">
            <div className="dashboard-summary__card summary-income">
              <div className="summary-label">Tổng thu nhập</div>
              <div className="summary-value">
                {stats.totalIncome ? stats.totalIncome.toLocaleString() : "none"} <span>VNĐ</span>
              </div>
            </div>
            <div className="dashboard-summary__card summary-courses">
              <div className="summary-label">Số khóa học đã tạo</div>
              <div className="summary-value">{stats.totalCourses}</div>
            </div>
            <div className="dashboard-summary__card summary-registrations">
              <div className="summary-label">Tổng số lượt đăng kí</div>
              <div className="summary-value">{stats.totalRegistrations}</div>
            </div>
          </div>

          {/* Biểu đồ */}
          <div className="dashboard-charts">
            <div className="dashboard-chart">
              <h5>Thu nhập theo tháng</h5>
              <ResponsiveContainer width="100%" height={250}>
                <LineChart data={instructorStats}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis dataKey="totalEarnings" />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="totalEarnings" stroke="#28a745" name="Thu nhập" />
                </LineChart>
              </ResponsiveContainer>
            </div>
            <div className="dashboard-chart">
              <h5>Lượt đăng ký theo tháng</h5>
              <ResponsiveContainer width="100%" height={250}>
                <LineChart data={instructorStats}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line type="monotone" dataKey="totalRegistrations" stroke="#007bff" name="Số lượt đăng ký" />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </div>

          {/* Bảng thống kê khóa học */}
          <CourseStatisticsTable courseStats={stats.courseStats} />
        </>
      )}
    </section>
  );
}
