import React, { useState } from 'react';

const UploadVideo = () => {
  const [video, setVideo] = useState(null);

  const handleFileChange = (e) => {
    setVideo(e.target.files[0]);
  };

  const uploadVideo = (e) => {
    e.preventDefault();

    // Tạo đối tượng FormData từ form
    const formData = new FormData(e.target);

    // Không cần phải thêm file vào formData vì input type="file" sẽ tự động làm điều đó
    // formData.append('file', video); // không cần thiết nếu bạn đã có input type="file"

    fetch('http://localhost:8080/online_learning/lessons', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: formData,  // Gửi formData, trình duyệt tự động xử lý Content-Type
    })
    .then((response) => response.json())
    .then((data) => {
      console.log(data); // Hiển thị kết quả
    })
    .catch((error) => {
      console.error('Error:', error); // Hiển thị lỗi nếu có
    });
  };

  return (
    <div>
      <form onSubmit={uploadVideo}>
        <input type="file" name="file" onChange={handleFileChange} />
        <input type="text" name="title" placeholder="title" />
        <input type="text" name="courseId" placeholder="courseId" />
        <button type="submit">Tải video lên</button>
      </form>
    </div>
  );
};

export default UploadVideo;
