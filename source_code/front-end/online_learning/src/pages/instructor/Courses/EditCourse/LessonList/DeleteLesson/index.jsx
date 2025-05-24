import { deleteLesson } from "utils/InstructorUtil/LessonUtil";
import Swal from "sweetalert2";

export default function DeleteLesson(props) {
    const { lessonId, handleDelete, children } = props;

    const handleDeleteBackend = () => {

        // xác nhận muốn xóa
        Swal.fire({
            title: 'Bạn có chắc chắn muốn xóa bài học này?',
            text: "Bài học sẽ bị xóa vĩnh viễn!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Xóa!',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                // xóa bai học
                deleteLesson(lessonId)
                    .then(() => {
                        console.log("Lesson deleted successfully");
                    })
                    .catch((error) => {
                        console.error("Error deleting lesson:", error);
                    });
                handleDelete(lessonId);


                Swal.fire(
                    'Đã xóa!',
                    'Bài học đã được xóa.',
                    'success'
                )
            }
        })


    }


    return (
        <>
            <div
                onClick={handleDeleteBackend}
            >
                {children}
            </div>
        </>
    );
}