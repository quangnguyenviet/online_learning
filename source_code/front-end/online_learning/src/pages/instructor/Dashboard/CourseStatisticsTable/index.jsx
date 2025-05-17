import { useEffect, useState } from "react";
import { getCourseStatistics } from "utils/InstructorUtil/StatisticUtil"

export default function CourseStatisticsTable({courseStats}) {

    console.log(courseStats);

    
    

    return (
        <>
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
                                    <td>{course.title}</td>
                                    <td>{course.totalRegistrations}</td>
                                    <td>{course.totalEarnings.toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    )
}