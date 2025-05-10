import React from "react";
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer,
} from "recharts";
import "./style.scss";

const incomeData = [
  { month: "Jan", income: 1200000 },
  { month: "Feb", income: 1500000 },
  { month: "Mar", income: 1800000 },
  { month: "Apr", income: 1000000 },
  { month: "May", income: 2200000 },
];

const registrationData = [
  { month: "Jan", registrations: 20 },
  { month: "Feb", registrations: 35 },
  { month: "Mar", registrations: 45 },
  { month: "Apr", registrations: 30 },
  { month: "May", registrations: 60 },
];

const courseStats = [
  { name: "Lập trình Python cơ bản", registrations: 45, income: 4500000 },
  { name: "React cho người mới bắt đầu", registrations: 30, income: 3000000 },
  { name: "Thiết kế Database", registrations: 25, income: 2500000 },
];

export default function InstructorDashboard() {
  return (
    <div className="container my-4 instructor-dashboard">
      <h2 className="mb-4">Bảng điều khiển Giảng viên</h2>

      {/* Tổng quan */}
      <div className="row mb-4">
        <div className="col-md-4">
          <div className="card text-white bg-success mb-3">
            <div className="card-body">
              <h5 className="card-title">Tổng thu nhập</h5>
              <p className="card-text">9,500,000 VNĐ</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-white bg-primary mb-3">
            <div className="card-body">
              <h5 className="card-title">Số khóa học đã tạo</h5>
              <p className="card-text">3</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card text-white bg-info mb-3">
            <div className="card-body">
              <h5 className="card-title">Tổng học viên</h5>
              <p className="card-text">100+</p>
            </div>
          </div>
        </div>
      </div>

      {/* Biểu đồ */}
      <div className="row mb-4">
        <div className="col-md-6">
          <h5>Thu nhập theo tháng</h5>
          <ResponsiveContainer width="100%" height={250}>
            <LineChart data={incomeData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="income" stroke="#28a745" />
            </LineChart>
          </ResponsiveContainer>
        </div>
        <div className="col-md-6">
          <h5>Lượt đăng ký theo tháng</h5>
          <ResponsiveContainer width="100%" height={250}>
            <LineChart data={registrationData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="registrations" stroke="#007bff" />
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Bảng thống kê khóa học */}
      <div className="card mt-4">
        <div className="card-body">
          <h5 className="card-title mb-3">Thống kê khóa học</h5>
          <table className="table table-bordered">
            <thead className="thead-light">
              <tr>
                <th>Tên khóa học</th>
                <th>Lượt đăng ký</th>
                <th>Thu nhập (VNĐ)</th>
              </tr>
            </thead>
            <tbody>
              {courseStats.map((course, idx) => (
                <tr key={idx}>
                  <td>{course.name}</td>
                  <td>{course.registrations}</td>
                  <td>{course.income.toLocaleString()}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
