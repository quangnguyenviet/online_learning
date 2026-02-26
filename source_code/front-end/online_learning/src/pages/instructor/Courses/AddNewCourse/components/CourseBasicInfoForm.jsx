import React from 'react';
import styles from '../AddNewCourse.module.scss';

export default function CourseBasicInfoForm({ 
    categories, 
    priceDisplay, 
    onPriceChange 
}) {
    return (
        <div className={styles.mainCard}>
            <section className={styles.section}>
                <label>Tiêu Đề Khóa Học</label>
                <input 
                    name="title" 
                    type="text" 
                    required={true} 
                    placeholder="ví dụ: Advanced React Patterns" 
                />
            </section>

            <section className={styles.section}>
                <label>Mô Tả Ngắn</label>
                <textarea 
                    name="shortDesc" 
                    rows="3" 
                    placeholder="Tóm tắt ngắn cho thẻ khóa học..."
                ></textarea>
            </section>

            <div className={styles.row}>
                <section className={styles.section}>
                    <label>Danh Mục</label>
                    <select name="categoryId">
                        <option value={""}>Chọn danh mục</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </section>
                <section name="level" className={styles.section}>
                    <label>Mức Độ Khó</label>
                    <select name="level">
                        <option value={""}>Chọn mức độ</option>
                        <option value="BEGINNER">Người Mới Bắt Đầu</option>
                        <option value="INTERMEDIATE">Trung Bình</option>
                        <option value="ADVANCED">Nâng Cao</option>
                        <option value="ALL_LEVELS">Tất Cả Mức Độ</option>
                    </select>
                </section>
            </div>

            <div className={styles.row}>
                <section className={styles.section}>
                    <label>Giá (VND)</label>
                    <input
                        type="text"
                        required={true}
                        placeholder="1,000,000"
                        value={priceDisplay}
                        onChange={onPriceChange}
                    />
                </section>
                <section className={styles.section}>
                    <label>Phần trăm giảm (%)</label>
                    <input 
                        name="discount" 
                        type="number" 
                        placeholder="Tùy Chọn: 50"
                        inputMode="numeric" 
                        min="0" 
                        max="100"
                        step="1"
                    />
                </section>
            </div>
        </div>
    );
}
