import { useState } from "react";

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

        fetch(`http://localhost:8080/api/courses/${courseId}/price`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ price: Number(newPrice) }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to update price");
                }
                return response.json();
            })
            .then(() => {
                alert("Cập nhật giá thành công!");
                setPrice(newPrice); // cập nhật giá mới vào view
            })
            .catch((error) => {
                console.error("Error updating price:", error);
                alert("Cập nhật giá thất bại!");
            })
            .finally(() => {
                setUpdating(false);
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
