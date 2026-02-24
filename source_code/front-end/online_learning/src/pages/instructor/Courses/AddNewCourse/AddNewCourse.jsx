import React, { useEffect, useState } from 'react';
import { FaCloudUploadAlt, FaSave, FaTimes, FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import styles from './AddNewCourse.module.scss';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
import CategoryApi from 'service/apis/CategoryApi';
import CourseApi from 'service/apis/CourseApi';
import { useLoading } from 'components/common/Loading/Loading';

export default function AddNewCourse() {
    const navigate = useNavigate();
    const [thumbnail, setThumbnail] = useState(null);
    const [categories, setCategories] = useState([]);

    const [objectives, setObjectives] = useState([]);
    const [objectiveInput, setObjectiveInput] = useState('');

    // Price input handling
    const [priceDisplay, setPriceDisplay] = useState('');
    const [priceValue, setPriceValue] = useState('');
    const formatNumber = (value) => {
        if (!value) return '';
        return value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    };
    const handlePriceChange = (e) => {
        // Remove all non-digit characters
        const raw = e.target.value.replace(/\D/g, '');

        setPriceValue(raw);
        setPriceDisplay(formatNumber(raw));
    };



    const { ErrorDisplay, showError } = useError();
    const { LocalLoading, showLoading, hideLoading } = useLoading();

    const fetchCategories = async () => {
        // Fetch categories from API
        try {
            const response = await CategoryApi.getAllCategories();
            console.log('Fetched categories:', response);
            setCategories(response.data || []);
        } catch (error) {
            showError('Failed to load categories. Please try again later.');
        }
    };

    useEffect(() => {
        fetchCategories();
    }, [])

    const handleFileChange = (e) => {
        if (e.target.files && e.target.files[0]) {
            setThumbnail(URL.createObjectURL(e.target.files[0]));
        }
    };
    const handleClickSave = () => {
        // invoke form submission
        document.getElementById('addNewCourseForm').requestSubmit();

    }
    const handCreateCourse = async (e) => {
        e.preventDefault();
        showLoading();
        console.log('Creating course...');
        const formData = new FormData(e.target);
        console.log(...formData);
        // check category
        if (!formData.get('categoryId') || formData.get('categoryId') === "") {
            showError('Vui lòng chọn danh mục cho khóa học.');
            return;
        }
        // check level
        if (!formData.get('level') || formData.get('level') === "") {
            showError('Vui lòng chọn mức độ cho khóa học.');
            return;
        }
        // check discount if not present set to 0
        const discount = formData.get('discount');
        if (!discount) {
            formData.set('discount', '0');
        }

        // append learning objectives
        objectives.forEach(obj => {
            // check if obj is not empty
            if (obj.trim() !== '') {
                formData.append('objectives', obj);
            }
        });

        // append price
        formData.set('price', priceValue || '0');


        console.log(...formData);

        try {
            const response = await CourseApi.createCourse(formData);
            hideLoading();
            navigate('/instructor/courses');
        } catch (error) {
            hideLoading();
            showError('Đã xảy ra lỗi khi tạo khóa học. Vui lòng thử lại sau.');
        }

    }

    const handleAddObjective = () => {
        if (!objectiveInput.trim()) return;

        setObjectives(prev => [...prev, objectiveInput.trim()]);
        setObjectiveInput('');
    };

    const handleRemoveObjective = (index) => {
        setObjectives(prev => prev.filter((_, i) => i !== index));
    };


    return (
        <>
            <ErrorDisplay />
            <LocalLoading />
            <div className={styles.container}>
                <header className={styles.header}>
                    <div>
                        <h1>Tạo Khóa Học Mới</h1>
                        <p>Điền thông tin chi tiết bên dưới để thiết lập chương trình học tập mới của bạn.</p>
                    </div>
                    <div className={styles.actions}>
                        <button
                            className={styles.btnPrimary} onClick={handleClickSave}>
                            <FaSave /> Lưu Khóa Học
                        </button>
                    </div>
                </header>

                <form id="addNewCourseForm" className={styles.formGrid} onSubmit={handCreateCourse}>
                    {/* Left Column: General Info */}
                    <div className={styles.mainCard}>
                        <section className={styles.section}>
                            <label>Tiêu Đề Khóa Học</label>
                            <input name="title" type="text" required={true} placeholder="ví dụ: Advanced React Patterns" />
                        </section>

                        <section className={styles.section}>
                            <label>Mô Tả Ngắn</label>
                            <textarea name="shortDesc" rows="3" placeholder="Tóm tắt ngắn cho thẻ khóa học..."></textarea>
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
                                    onChange={handlePriceChange}
                                />

                            </section>
                            <section className={styles.section}>
                                <label>Phần trăm giảm  (%)</label>
                                <input name="discount" type="number" placeholder="Tùy Chọn: 50" 
                                inputMode="numeric" min="0" max="100"
                                step="1"
                                />
                            </section>
                        </div>
                    </div>

                    {/* Right Column: Media & Settings */}
                    <div className={styles.sideCard}>
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
                                <input type="file" name='imageFile' required onChange={handleFileChange} accept="image/*" />
                            </div>
                        </section>

                        <section className={styles.section}>
                            <label>Mục Tiêu Học Tập</label>

                            <div className={styles.bulletInput}>
                                <input
                                    type="text"
                                    placeholder="Thêm mục tiêu..."
                                    value={objectiveInput}
                                    onChange={(e) => setObjectiveInput(e.target.value)}
                                    onKeyDown={(e) => {
                                        if (e.key === 'Enter') {
                                            e.preventDefault();
                                            handleAddObjective();
                                        }
                                    }}
                                />
                                <button type="button" onClick={handleAddObjective}>
                                    <FaPlus />
                                </button>
                            </div>

                            <ul className={styles.bulletList}>
                                {objectives.map((item, index) => (
                                    <li key={index}>
                                        {item}
                                        <button
                                            type="button"
                                            onClick={() => handleRemoveObjective(index)}
                                            className={styles.removeBtn}
                                        >
                                            <FaTimes />
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </section>

                    </div>
                </form>
            </div>
        </>

    );
}