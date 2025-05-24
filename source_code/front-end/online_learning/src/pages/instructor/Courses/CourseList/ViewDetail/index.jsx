import { FaEdit, FaBookOpen, FaCheckCircle, FaTasks, FaListAlt } from "react-icons/fa";
import "./ViewDetail.scss";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters
import { getCourseById } from "utils/CoursesUtil"; // Import the function to get course by ID
import CustomModal from "components/CustomModal"; // Import the custom modal component
import EditPrice from "pages/instructor/Courses/EditCourse/EditPrice"; // Import the EditPrice component
import { EditConditions } from "pages/instructor/Courses/EditCourse/EditConditons";
import { EditWillLearn } from "pages/instructor/Courses/EditCourse/EditWillLearn"; // Import the EditWillLearn component
import { LessonList } from "pages/instructor/Courses/EditCourse/LessonList"; // Import the LessonList component
import EditGeneral from "../../EditCourse/EditGeneral";
import { createContext, useContext } from 'react';

export const ModalContext = createContext(null);

export default function ViewDetail() {

    const [showModal, setShowModal] = useState(false);
    const [tag, setTag] = useState("tag");


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

    const handleGeneralEdit = () => {
        setTag("general");
        setShowModal(true);
    }
    const handleConditionsEdit = () => {
        setTag("conditions");
        setShowModal(true);
    }
    const handleWillLearnEdit = () => {
        setTag("willLearn");
        setShowModal(true);
    }
    const handleLessonEdit = () => {
        setTag("lesson");
        setShowModal(true);
    }

    console.log("data", data.lessons);

    const handleTogglePublish = () => {

        console.log("Updated course data:");
    }


    return (
        <ModalContext.Provider value={{ setShowModal, setData }}>
            <div className="view-detail-container">
                <h1 className="main-title">📘 Chi tiết khóa học</h1>

                <div className="publish-control">
                    <button
                        className={`btn-publish ${data.published ? "revoke" : "publish"}`}
                        onClick={handleTogglePublish}
                    >
                        {data.published ? "Thu hồi khóa học" : "Xuất bản khóa học"}
                    </button>
                </div>



                <section className="section">
                    <div className="section-header">
                        <h2><FaBookOpen className="icon icon-blue" /> Thông tin chung</h2>
                        <button className="btn-edit" onClick={handleGeneralEdit}><FaEdit /> Sửa</button>
                    </div>
                    <ul className="section-list">
                        <li><strong>Tên khóa học:</strong> {data.title}</li>
                        <li><strong>Giá khóa học:</strong> {data.price && data.price.toLocaleString()} VNĐ</li>
                        <li><strong>Mô tả:</strong> {data.short_desc && data.short_desc}</li>
                    </ul>
                </section>

                <section className="section">
                    <div className="section-header">
                        <h2><FaCheckCircle className="icon icon-green" /> Sau khóa học bạn sẽ học được</h2>
                        <button className="btn-edit" onClick={handleWillLearnEdit}><FaEdit /> Sửa</button>
                    </div>
                    <ul className="section-list">
                        {data.learnWhats && data.learnWhats.map((item, index) => {
                            return (
                                <li key={index}>✅{item.description}</li>
                            )
                        }
                        )}
                    </ul>
                </section>

                <section className="section">
                    <div className="section-header">
                        <h2><FaTasks className="icon icon-orange" /> Điều kiện để học khóa học</h2>
                        <button className="btn-edit" onClick={handleConditionsEdit}><FaEdit /> Sửa</button>
                    </div>
                    <ul className="section-list">
                        {data.requires && data.requires.map((item, index) => {
                            return (
                                <li key={index}>🔒{item.description}</li>
                            )
                        }
                        )}

                    </ul>
                </section>

                <section className="section">
                    <div className="section-header">
                        <h2><FaListAlt className="icon icon-purple" /> Danh sách bài học</h2>
                    </div>
                    <ul className="section-list">
                        {/* {data.lessons && data.lessons.map((item, index) => {
                            return (
                                <li key={index}>📄{item.title}</li>
                            )
                        })} */}
                        {data.lessons && <LessonList lessons={data.lessons} />}
                    </ul>
                </section>


                <CustomModal
                    title=""
                    isOpen={showModal}
                    onClose={() => setShowModal(false)}
                >
                    {tag === "general" && (
                        <EditGeneral data={data} />
                    )}
                    {tag === "conditions" && (
                        <EditConditions conditions={data.requires} />
                    )}
                    {tag === "willLearn" && (
                        <EditWillLearn willLearns={data.learnWhats} />
                    )}
                    {tag === "lesson" && (
                        <LessonList lessons={data.lessons} />
                    )}
                </CustomModal>
            </div>
        </ ModalContext.Provider>




    );
}
