export default function EditLesson(props) {
    const { lesson, handleEditFormChange, handleSaveEdit} = props;



    return (
        <>
            <div style={{ marginTop: "15px" }}>
                            <label>Tên bài học:</label>
                            <input
                                type="text"
                                defaultValue={lesson.title}
                                onChange={(e) => handleEditFormChange("title", e.target.value)}
                                style={{
                                    width: "100%",
                                    marginTop: "5px",
                                    marginBottom: "10px"
                                }}
                            />
                            <label>Mô tả bài học:</label>
                            <textarea
                                defaultValue={lesson.description}
                                onChange={(e) => handleEditFormChange("description", e.target.value)}
                                rows="3"
                                style={{
                                    width: "100%",
                                    marginTop: "5px",
                                    marginBottom: "10px"
                                }}
                            />
                            <label>Upload video mới:</label>
                            <input
                                type="file"
                                accept="video/*"
                                onChange={(e) => handleEditFormChange("video", e.target.files[0])}
                                style={{ display: "block", marginBottom: "10px" }}
                            />
                            <button onClick={() => handleSaveEdit(lesson.id)}>Lưu</button>
                        </div>
        </>
    );
}