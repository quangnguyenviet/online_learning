import React, { use, useState, useEffect } from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
} from "recharts";
import "./style.scss";
import CourseStatisticsTable from "./CourseStatisticsTable";
import { getCourseStatistics } from "utils/InstructorUtil/StatisticUtil";
import { getInstructorStattic } from "utils/InstructorUtil/InstructorStatisticUtil";

const incomeData = [
  { month: "Jan", income: 1200000 },
  { month: "Feb", income: 1500000 },
  { month: "Mar", income: 1800000 },
  { month: "Apr", income: 1000000 },
  { month: "May", income: 2200000 },
  { month: "May", income: 2200000 },
];

const registrationData = [
  { month: "Jan", registrations: 20 },
  { month: "Feb", registrations: 35 },
  { month: "Mar", registrations: 45 },
  { month: "Apr", registrations: 30 },
  { month: "May", registrations: 60 },
];


export default function InstructorDashboard() {
  const [stats, setStats] = useState("");
  const [instructorStats, setInstructorStats] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getCourseStatistics()
      .then((result) => {
        if (result) {
          setStats(result);
          
        }
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
      })
    

  }, []);




  return (
    <>
      {loading ? (
        loading
      ) : (
        <>
          <div className="container my-4 instructor-dashboard">
            <h2 className="mb-4">Bảng điều khiển Giảng viên</h2>

            {/* Tổng quan */}
            <div className="row mb-4">
              <div className="col-md-4">
                <div className="card text-white bg-success mb-3">
                  <div className="card-body">
                    <h5 className="card-title">Tổng thu nhập</h5>
                    <p className="card-text">{stats.totalIncome ? (
                       stats.totalIncome.toLocaleString()
                    ) : (
                      "none"
                    )} VNĐ</p>
                  </div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="card text-white bg-primary mb-3">
                  <div className="card-body">
                    <h5 className="card-title">Số khóa học đã tạo</h5>
                    <p className="card-text">{stats.totalCourses}</p>
                  </div>
                </div>
              </div>
              <div className="col-md-4">
                <div className="card text-white bg-info mb-3">
                  <div className="card-body">
                    <h5 className="card-title">Tổng số lượt đăng kí</h5>
                    <p className="card-text">{stats.totalRegistrations}</p>
                  </div>
                </div>
              </div>
            </div>

            {/* Biểu đồ */}
            <div className="row mb-4">
              <div className="col-md-6">
                <h5>Thu nhập theo tháng</h5>
                <ResponsiveContainer width="100%" height={250}>
                  <LineChart data={instructorStats}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="month" />
                    <YAxis
                      dataKey="totalEarnings"
                    />

                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="totalEarnings" stroke="#28a745" name="thu nhập" />
                  </LineChart>
                </ResponsiveContainer>
              </div>
              <div className="col-md-6">
                <h5>Lượt đăng ký theo tháng</h5>
                <ResponsiveContainer width="100%" height={250}>
                  <LineChart data={instructorStats}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="month" />
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Line type="monotone" dataKey="totalRegistrations" stroke="#007bff" name = "Số lượt đăng ký theo tháng" />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            </div>

            {/* Bảng thống kê khóa học */}
            <CourseStatisticsTable courseStats={stats.courseStats} />

          </div>
        </>
      )}
    </>

  );
}
