import { FaBookOpen, FaCheckCircle, FaTasks, FaListAlt } from "react-icons/fa";
import styles from "./ViewDetail.module.scss";
import { useEffect, useState, createContext } from "react";
import { useParams } from "react-router-dom";
import { getCourseById } from "utils/CoursesUtil";
import { LessonList } from "pages/instructor/Courses/LessonList/LessonList";
import CourseSection from "./components/CourseSection";
import GeneralInfo from "./components/GeneralInfo";
import PublishControl from "./components/PublishControl";
import useEditModal from "./hooks/useEditModal";
import { useSuccess } from "components/common/SucessDisplay/SuccessDisplay";
import Objective from "./components/Objective";
import CourseApi from "service/apis/CourseApi";


export const ModalContext = createContext(null);

export default function ViewDetail() {
    const { courseId } = useParams();
    const [data, setData] = useState({});
    const [loading, setLoading] = useState(true);
    
    const { openModal, setShowModal, CustomModal } = useEditModal({ data, setData });
    const {SuccessDisplay, showSuccess} = useSuccess();

    const fetchCourseById = async (id) => {
        try {
            const response = await CourseApi.getCourseById(id);
            setData(response.data);
            setLoading(false);
            console.log(response);
        } catch (error) {
            console.error("Error fetching course by ID:", error);
            throw error;
        }
    };

    useEffect(() => {
        fetchCourseById(courseId);
    }, [courseId]);

    const handleTogglePublish = async (isPublished) => {
        console.log("Toggle publish status", isPublished);

        try {
            const response = await CourseApi.publishCourse(courseId, isPublished);
            console.log("Publish response:", response);
            showSuccess("Cập nhật trạng thái khóa học thành công!");
            setData(prev => ({ ...prev, published: isPublished }));
        } catch (error) {
            console.error("Error updating publish status:", error);
        }
    };

    if (loading) {
        return (
            <div className={styles.viewDetailContainer}>
                <div className={styles.loadingState}>
                    <h2>Đang tải dữ liệu khóa học...</h2>
                </div>
            </div>
        );
    }

    return (
        <ModalContext.Provider value={{ setShowModal, setData, showSuccess }}>
            
            <div className={styles.viewDetailContainer}>
                <SuccessDisplay />
                <h1 className={styles.mainTitle}>📘 Chi tiết khóa học</h1>

                <PublishControl 
                    isPublished={data.published} 
                    onToggle={handleTogglePublish}
                    courseTitle={data.title}
                    courseId={courseId}
                />

                <CourseSection
                    icon={<FaBookOpen className={`${styles.icon} ${styles.iconBlue}`} />}
                    title="Thông tin cơ bản"
                    onEdit={() => openModal("general")}
                >
                    <GeneralInfo 
                        title={data.title}
                        price={data.price}
                        description={data.shortDesc}
                        category={data.category?.name}
                        imageUrl={data.imageUrl}
                        discount={data.discount}
                    />
                </CourseSection>

                <CourseSection
                    icon={<FaCheckCircle className={`${styles.icon} ${styles.iconGreen}`} />}
                    title="Mục tiêu khóa học"
                    onEdit={() => openModal("willLearn")}
                >
                    <Objective outcomes={data.objectives} />
                </CourseSection>

                {/* <CourseSection
                    icon={<FaTasks className={`${styles.icon} ${styles.iconOrange}`} />}
                    title="Điều kiện để học khóa học"
                    onEdit={() => openModal("conditions")}
                >
                    <CourseRequirements requirements={data.requires} />
                </CourseSection> */}

                <CourseSection
                    icon={<FaListAlt className={`${styles.icon} ${styles.iconPurple}`} />}
                    title="Danh sách bài học"
                    showEditButton={false}
                >
                    <div className={`${styles.sectionList} ${styles.lessonsList}`}>
                        {data.lessons && <LessonList lessons={data.lessons} />}
                    </div>
                </CourseSection>

                <CustomModal />
            </div>
        </ModalContext.Provider>
    );
}
