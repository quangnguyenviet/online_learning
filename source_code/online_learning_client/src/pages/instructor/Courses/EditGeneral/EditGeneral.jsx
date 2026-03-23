import { useContext, useEffect, useState } from "react";
import { FaBook, FaMoneyBillWave, FaAlignLeft, FaSave, FaImage, FaTag, FaPercent } from "react-icons/fa";
import styles from "./EditGeneral.module.scss";
import { ModalContext } from "pages/instructor/Courses/ViewDetail/ViewDetail";
import CategoryApi from "service/apis/CategoryApi";
import CourseApi from "service/apis/CourseApi";
import { useLoading } from "components/common/Loading/Loading";
const FormField = ({ label, icon: Icon, iconClass, children }) => (
    <>
        <h2 className={styles.fieldLabel}>
            <Icon className={`${styles.icon} ${iconClass}`} /> {label}
        </h2>
        {children}
    </>
);

export default function EditGeneral({ data }) {
    console.log("EditGeneral data:", data);
    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState(data.imageUrl || null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const { setShowModal, setData } = useContext(ModalContext);

    const [allCategories, setAllCategories] = useState([]);
    const { GlobalLoading, showLoading, hideLoading } = useLoading();

    useEffect(() => {
        const fetchingCategories = async () => {
            try {
                showLoading();
                const response = await CategoryApi.getAllCategories();
                
                setAllCategories(response.data);
                hideLoading();
            } catch (error) {
                console.error("Error fetching categories:", error);
                hideLoading();
            }
        };
        fetchingCategories();
    }, []);


    const handleImageChange = (e) => {
        const file = e.target.files[0];
        if (file) {
            setImage(file);
            const reader = new FileReader();
            reader.onloadend = () => setImagePreview(reader.result);
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData(e.target);
        // add course id
        formData.append("id", data.id);
        console.log("Form Data Entries:");
        for (let pair of formData.entries()) {
            console.log(`${pair[0]}: ${pair[1]}`);
        }
        
        // call update api
        try {
            setIsSubmitting(true);
            showLoading();
            const response = await CourseApi.updateCourse(formData);
            hideLoading();
            console.log("Update response:", response);
            setData(response.data);
            setShowModal(false);
        } catch (error) {
            console.error("Error updating course:", error);
            hideLoading();
        } finally {
            setIsSubmitting(false);
        }
        
    };

    return (
        <form className={`${styles.editGeneralContainer} ${styles.relativePosition}`} onSubmit={handleSubmit}>
            <GlobalLoading />
            
            <FormField label="Hình ảnh khóa học" icon={FaImage} iconClass={styles.blue}>
                <div className={styles.imageUploadWrapper}>
                    {imagePreview && (
                        <div className={styles.imagePreview}>
                            <img src={imagePreview} alt="Preview" />
                        </div>
                    )}
                    <input
                        type="file"
                        id="courseImage"
                        className={styles.fileInput}
                        accept="image/*"
                        name="imageFile"
                        onChange={handleImageChange}
                    />
                    <label htmlFor="courseImage" className={styles.fileLabel}>
                        <FaImage /> {imagePreview ? "Thay đổi hình ảnh" : "Chọn hình ảnh"}
                    </label>
                </div>
            </FormField>

            <FormField label="Tên khóa học" icon={FaBook} iconClass={styles.purple}>
                <input
                    type="text"
                    name="title"
                    className={styles.formControl}
                    defaultValue={data.title}
                    placeholder="Nhập tên khóa học"
                    required
                />
            </FormField>

            <FormField label="Giá khóa học (VNĐ)" icon={FaMoneyBillWave} iconClass={styles.green}>
                <input
                    type="number"
                    name="price"
                    className={styles.formControl}
                    defaultValue={data.price}
                    min={0}
                    placeholder="Nhập giá khóa học"
                    required
                />
            </FormField>

            <FormField label="Giảm giá (%)" icon={FaPercent} iconClass={styles.orange}>
                <input
                    type="number"
                    name="discount"
                    className={styles.formControl}
                    defaultValue={data.discount}
                    min={0}
                    max={100}
                    placeholder="Nhập phần trăm giảm giá"
                />
            </FormField>

            <FormField label="Danh mục khóa học" icon={FaTag} iconClass={styles.purple}>
                <select
                    name="categoryId"
                    className={styles.formControl}
                    defaultValue={data.category?.id || data.categoryId || ""}
                    key={allCategories.length}
                    required
                >
                    <option value="">-- Chọn danh mục --</option>
                    {allCategories.map((cat) => (
                        <option key={cat.id} value={cat.id}>
                            {cat.name}
                        </option>
                    ))}
                </select>
            </FormField>

            <FormField label="Mô tả ngắn" icon={FaAlignLeft} iconClass={styles.blue}>
                <textarea
                    name="shortDesc"
                    className={styles.formControl}
                    defaultValue={data.shortDesc}
                    rows={4}
                    placeholder="Nhập mô tả ngắn cho khóa học"
                    required
                ></textarea>
            </FormField>

            <button type="submit" className={styles.saveBtn} disabled={isSubmitting}>
                <FaSave /> {isSubmitting ? "Đang lưu..." : "Lưu"}
            </button>
        </form>
    );
}
