import { useState } from "react";
import { setPrice as setPriceUtil } from "utils/InstructorUtil/CourseUtil";
import { FaMoneyBillWave, FaEdit } from "react-icons/fa";
import "./style.scss";

export default function EditPrice({ price: initialPrice, courseId, data }) {
    const [price, setPrice] = useState(initialPrice);
    const [newPrice, setNewPrice] = useState(initialPrice);
    const [updating, setUpdating] = useState(false);

    const handleUpdatePrice = () => {
        if (Number(newPrice) <= 0) {
            alert("Giá phải lớn hơn 0");
            return;
        }

        setUpdating(true);
        setPriceUtil(courseId, newPrice)
            .then((response) => {
                if (response.status === 1000) {
                    setPrice(newPrice);
                    alert("Cập nhật giá thành công");
                }
            })
            .catch((error) => {
                console.error("Error updating price:", error);
                alert("Cập nhật giá thất bại");
            })
            .finally(() => {
                setUpdating(false);
            });
    };

    return (
        <div className="edit-price-container">
            <div className="section">
                <h2><FaMoneyBillWave className="icon-green" /> Tên khóa học</h2>
                <div className="input-group">
                    <span className="input-group-text"><FaEdit /></span>
                    <input type="text" className="form-control" name="title" defaultValue={data.title} />
                </div>
            </div>

            <div className="section">
                <h2><FaMoneyBillWave className="icon-green" /> Mô tả ngắn</h2>
                <div className="input-group">
                    <span className="input-group-text"><FaEdit /></span>
                    <input type="text" className="form-control" name="short_desc" defaultValue={data.short_desc} />
                </div>
            </div>

            <div className="section">
                <h2><FaMoneyBillWave className="icon-green" /> Giá khóa học</h2>
                <p className="current-price"><strong>Hiện tại:</strong> {price.toLocaleString()} VNĐ</p>
                <div className="input-group">
                    <span className="input-group-text"><FaEdit /></span>
                    <input
                        type="number"
                        className="form-control"
                        value={newPrice}
                        onChange={(e) => setNewPrice(e.target.value)}
                        min="0"
                        placeholder="Nhập giá mới"
                    />
                    <button
                        className="btn btn-success"
                        onClick={handleUpdatePrice}
                        disabled={updating || Number(newPrice) === Number(price)}
                    >
                        {updating ? "Đang cập nhật..." : "Cập nhật"}
                    </button>
                </div>
            </div>
        </div>
    );
}
