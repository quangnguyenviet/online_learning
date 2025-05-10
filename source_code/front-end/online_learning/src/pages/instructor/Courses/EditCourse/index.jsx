import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters
import { getCourseById } from "utils/CoursesUtil";
import EditPrice from "./EditPrice";
import { EditConditions } from "./EditConditons";
import { EditWillLearn } from "./EditWillLearn";
import { LessonList } from "./LessonList";

export function EditCourse() {
    const { courseId } = useParams(); // Get courseId from URL parameters
    const [data, setData] = useState([]); // State to hold course conditions data
    const [loading, setLoading] = useState(true); // State to manage loading state

    useEffect(() => {
        getCourseById(courseId)
            .then((response) => {
                const data = response.data;
                console.log(data);
                setData(data); // Set the fetched data to state
                setLoading(false); // Set loading to false after data is fetched
            })
            .catch((error) => {
                console.error("Error fetching course conditions:", error);
            });
    }, [courseId]);


    return (
        <div style={{ padding: "20px", margin: "auto" }}>
            <h1>Chỉnh sửa khóa học</h1>
            {loading === true ? (
                <h2>loading</h2>
            ) : (
                <>

                    {/* Giá khóa học */}
                    <EditPrice price={data.price} courseId={data.id}/>

                    <hr />

                    {/* Điều kiện học */}
                    <EditConditions  conditions={data.requires} />

                    <hr />

                    {/* Bạn sẽ học được */}
                    <EditWillLearn willLearns={data.learnWhats} />

                    <hr />

                    {/* Danh sách bài học */}
                    <LessonList lessons={data.lessons} />
                </>
            )}
        </div>
    );
}
