import Swal from "sweetalert2";
import LessonApi from "service/apis/LessonApi";
import { useLoading } from "components/common/Loading/Loading";

export default function DeleteLesson(props) {
    const { lessonId, handleDelete, children } = props;
    const { GlobalLoading, showLoading, hideLoading } = useLoading();

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
        }).then(async (result) => {
            if (result.isConfirmed) {
                showLoading();
                try {
                    // gọi api xóa bài học
                    const response = await LessonApi.deleteLesson(lessonId);
                    console.log("Xóa bài học thành công:", response);
                    // remove lesson from list and display success message
                    handleDelete(lessonId);
                } catch (error) {
                    console.error("Xóa bài học thất bại:", error);
                }
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