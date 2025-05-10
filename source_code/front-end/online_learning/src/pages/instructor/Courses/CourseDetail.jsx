// // src/pages/instructor/Courses/CourseDetail.jsx
// import { useEffect, useState } from "react";
// import { WillLearn } from "pages/instructor/Courses/WillLearn"; // Import WillLearn component
// import { CourseConditions } from "./CourseConditions";
// import { LessonsList } from "pages/instructor/Courses/LessonsList"; // Import LessonsList component
// import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters
// import { getCourseById } from "utils/CoursesUtil";
// import Price from "./Price";

// export function CourseDetail() {
//     const { courseId } = useParams(); // Get courseId from URL parameters
//     const [data, setData] = useState([]); // State to hold course conditions data
//     const [loading, setLoading] = useState(true); // State to manage loading state

//     useEffect(() => {
//         getCourseById(courseId)
//             .then((response) => {
//                 const data = response.data;
//                 console.log(data);
//                 setData(data); // Set the fetched data to state
//                 setLoading(false); // Set loading to false after data is fetched
//             })
//             .catch((error) => {
//                 console.error("Error fetching course conditions:", error);
//             });
//     }, [courseId]);


//     return (
//         <div style={{ padding: "20px", maxWidth: "800px", margin: "auto" }}>
//             <h1>Chỉnh sửa khóa học</h1>
//             {loading === true ? (
//                 <h2>loading</h2>
//             ) : (
//                 <>

//                     {/* Giá khóa học */}
//                     <Price price={data.price} courseId={data.id}/>

//                     {/* Điều kiện học */}
//                     <CourseConditions conditions={data.requires} />

//                     {/* Bạn sẽ học được */}
//                     <WillLearn willLearns={data.learnWhats} />

//                     {/* Danh sách bài học */}
//                     <LessonsList lessons={data.lessons} />
//                 </>
//             )}
//         </div>
//     );
// }
