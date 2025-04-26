import { deleteLesson } from "utils/InstructorUtil/LessonUtil";

export default function DeleteLesson(props) {
    const { lessonId, handleDelete } = props;

    const handleDeleteBackend = () => {
        
        deleteLesson(lessonId)
            .then(() => {
                console.log("Lesson deleted successfully");
            })
            .catch((error) => {
                console.error("Error deleting lesson:", error);
            });
        handleDelete(lessonId);

    }


    return (
        <>
            <button
                onClick={handleDeleteBackend}
                style={{ color: "red" }}
            >
                Xoá
            </button>
        </>
    );
}