import { useState } from 'react';

export default function AddCourse() {
    const [title, setTitle] = useState('');
    const [thumbnail, setThumbnail] = useState(null); // Ảnh khóa học
    const [category, setCategory] = useState('');
    const [price, setPrice] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        // Tạo FormData để gửi file + text
        const formData = new FormData();
        formData.append('title', title);
        formData.append('thumbnail', thumbnail);
        formData.append('category', category);
        formData.append('price', price);

        // Gửi dữ liệu lên API ở đây
        console.log("FormData submitted", {
            title,
            thumbnail,
            category,
            price
        });

        // TODO: gọi API POST để tạo khóa học
    };

    const handleThumbnailChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setThumbnail(file);
        }
    };

    return (
        <div className="container mt-5">
            <h2>Thêm Khóa Học Mới</h2>
            <form onSubmit={handleSubmit} encType="multipart/form-data">
                <div className="mb-3">
                    <label className="form-label">Tên Khóa Học</label>
                    <input
                        type="text"
                        className="form-control"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Ảnh Khóa Học</label>
                    <input
                        type="file"
                        className="form-control"
                        onChange={handleThumbnailChange}
                        accept="image/*"
                        required
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Danh Mục</label>
                    <select
                        className="form-select"
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                        required
                    >
                        <option value="">-- Chọn danh mục --</option>
                        <option value="web">Lập Trình Web</option>
                        <option value="mobile">Lập Trình Mobile</option>
                        <option value="data">Khoa Học Dữ Liệu</option>
                        <option value="design">Thiết Kế</option>
                    </select>
                </div>

                <div className="mb-3">
                    <label className="form-label">Giá Khóa Học (VNĐ)</label>
                    <input
                        type="number"
                        className="form-control"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required
                        min="0"
                    />
                </div>

                <button type="submit" className="btn btn-primary">Tạo Khóa Học</button>
            </form>
        </div>
    );
}
