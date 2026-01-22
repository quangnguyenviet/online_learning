import React from "react";
import "./EditLesson.scss";

export default function EditLesson({ lesson, handleSaveEdit }) {
   const [lessonData, setLessonData] = React.useState({
       title: lesson.title,
       description: lesson.description,
       videoFile: null,
   });
   const handleChange = (e) => {
       const { name, value, files } = e.target;
       console.log(e.target);
       console.log("handleChange:", name, value, files);
       setLessonData((prev) => ({
              ...prev,
                [name]: files ? files[0] : value,
         }));
        console.log("Updated lessonData:", lessonData);
    };

    const handleSaveClick = () => {
        console.log("in handle save click");
        // generate formdata to send
        const formData = new FormData();
        formData.append("title", lessonData.title);
        formData.append("description", lessonData.description);
        if (lessonData.videoFile) {
            formData.append("videoFile", lessonData.videoFile);
        }
        console.log("lessonData to save:", formData);

        handleSaveEdit(lesson.id, formData);
    }
   
    return (
            <div className="edit-lesson">
                <label htmlFor={`title-${lesson.id}`}>Tên bài học:</label>
                <input
                    id={`title-${lesson.id}`}
                    type="text"
                    defaultValue={lesson.title}
                    onChange={handleChange}
                    name="title"
                />

                <label htmlFor={`desc-${lesson.id}`}>Mô tả bài học:</label>
                <textarea
                    id={`desc-${lesson.id}`}
                    rows="3"
                    defaultValue={lesson.description}
                    onChange={handleChange}
                    name="description"
                />

                <label htmlFor={`video-${lesson.id}`}>Upload video mới: (nếu không có video mới, sẽ giữ nguyên video cũ)</label>
                <input
                    id={`video-${lesson.id}`}
                    type="file"
                    accept="video/*"
                    onChange={handleChange}
                    name="videoFile"
                />

                <button className="btn-save" type="button" onClick={handleSaveClick}>
                    Lưu
                </button>
            </div>
    );
}
