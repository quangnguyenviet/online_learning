import { useState } from "react";
import { setPrice as setPriceUtil } from "utils/InstructorUtil/CourseUtil";

export default function Price(props) {

    const { price: initialPrice, courseId } = props; // destructure props
    const [price, setPrice] = useState(initialPrice); // local price state
    const [newPrice, setNewPrice] = useState(initialPrice); // input field state
    const [updating, setUpdating] = useState(false); // loading state

    const handleUpdatePrice = () => {
        if (Number(newPrice) <= 0) {
            alert("Giá phải lớn hơn 0");
            return;
        }

        setUpdating(true);
        setPriceUtil(courseId, newPrice) // call setPrice function from CourseUtil
            .then((response) => {

                if(response.status === 1000){
                    setPrice(newPrice); // update local price state
                    alert("Cập nhật giá thành công");
                }

            })
            .catch((error) => {
                console.error("Error updating price:", error);
                alert("Cập nhật giá thất bại");
            })
            .finally(() => {
                setUpdating(false); // reset loading state
            });

    };

    return (
        <div style={{ marginBottom: "20px" }}>
            <h2>Giá Khóa Học</h2>
            <p>Giá hiện tại: {price} VNĐ</p>

            <div className="input-group" style={{ maxWidth: "300px" }}>
                <input
                    type="number"
                    className="form-control"
                    value={newPrice}
                    onChange={(e) => setNewPrice(e.target.value)}
                    min="0"
                />
                <button
                    className="btn btn-primary"
                    onClick={handleUpdatePrice}
                    disabled={updating || newPrice === price}
                >
                    {updating ? "Đang cập nhật..." : "Cập nhật giá"}
                </button>
            </div>
        </div>
    );
}
