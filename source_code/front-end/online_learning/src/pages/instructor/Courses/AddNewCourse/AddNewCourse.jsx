import React, { useEffect, useState } from 'react';
import { FaSave } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import styles from './AddNewCourse.module.scss';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
import { ErrorDisplay } from 'components/common/ErrorDisplay/ErrorDisplay';
import CategoryApi from 'service/apis/CategoryApi';
import CourseApi from 'service/apis/CourseApi';
import { useLoading } from 'components/common/Loading/Loading';
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import CourseBasicInfoForm from './components/CourseBasicInfoForm';
import CourseThumbnailUpload from './components/CourseThumbnailUpload';
import CourseLearningObjectives from './components/CourseLearningObjectives';


export default function AddNewCourse() {
    const BASE_URL = process.env.REACT_APP_BASE_URL;
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



    const { showError, dismissError, errorMessage } = useError();
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
    const validateFormData = (formData) => {
        if (!formData.get('categoryId') || formData.get('categoryId') === "") {
            showError('Vui lòng chọn danh mục cho khóa học.');
            return false;
        }
        if (!formData.get('level') || formData.get('level') === "") {
            showError('Vui lòng chọn mức độ cho khóa học.');
            return false;
        }
        return true;
    };

    const prepareFormData = (formData) => {
        // Set default discount if not provided
        if (!formData.get('discount')) {
            formData.set('discount', '0');
        }

        // Append learning objectives
        objectives.forEach(obj => {
            if (obj.trim() !== '') {
                formData.append('objectives', obj);
            }
        });

        // Set price
        formData.set('price', priceValue || '0');
    };

    const handleCreateSuccess = () => {
        hideLoading();
        navigate('/instructor/courses');
    };

    const handleCreateError = () => {
        hideLoading();
        showError('Đã xảy ra lỗi khi tạo khóa học. Vui lòng thử lại sau.');
    };

    const handCreateCourse = async (e) => {
        e.preventDefault();
        showLoading();

        const formData = new FormData(e.target);

        // Validate form data
        if (!validateFormData(formData)) {
            hideLoading();
            return;
        }

        // Prepare form data
        prepareFormData(formData);

        try {
            await CourseApi.createCourse(formData);
            handleCreateSuccess();
        } catch (error) {
            handleCreateError();
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
            <ErrorDisplay errorMessage={errorMessage} onDismiss={dismissError} />
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
                    <CourseBasicInfoForm 
                        categories={categories}
                        priceDisplay={priceDisplay}
                        onPriceChange={handlePriceChange}
                    />

                    {/* Right Column: Media & Settings */}
                    <div className={styles.sideCard}>
                        <CourseThumbnailUpload 
                            thumbnail={thumbnail}
                            onFileChange={handleFileChange}
                        />

                        <CourseLearningObjectives 
                            objectives={objectives}
                            objectiveInput={objectiveInput}
                            onInputChange={setObjectiveInput}
                            onAdd={handleAddObjective}
                            onRemove={handleRemoveObjective}
                        />
                    </div>
                </form>
            </div>
        </>

    );
}