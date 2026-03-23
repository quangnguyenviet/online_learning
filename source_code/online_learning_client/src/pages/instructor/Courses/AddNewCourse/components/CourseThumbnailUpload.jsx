import React from 'react';
import { FaCloudUploadAlt } from 'react-icons/fa';
import styles from '../AddNewCourse.module.scss';

export default function CourseThumbnailUpload({ thumbnail, onFileChange }) {
    return (
        <section className={styles.section}>
            <label>Hình Ảnh Đại Diện Khóa Học</label>
            <div className={styles.uploadArea}>
                {thumbnail ? (
                    <img src={thumbnail} alt="Preview" className={styles.preview} />
                ) : (
                    <div className={styles.uploadPlaceholder}>
                        <FaCloudUploadAlt />
                        <p>Kéo thả hoặc nhấp để tải lên</p>
                    </div>
                )}
                <input 
                    type="file" 
                    name='imageFile' 
                    required 
                    onChange={onFileChange} 
                    accept="image/*" 
                />
            </div>
        </section>
    );
}
