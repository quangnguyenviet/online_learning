import styles from "../ViewDetail.module.scss";
import { FaImage } from "react-icons/fa";
import { useLoading } from "components/common/Loading/Loading";

export default function GeneralInfo({ title, price, description, category, imageUrl, discount }) {
    const { LocalLoading } = useLoading();
    const discountedPrice = discount && price ? price - (price * discount) / 100 : null;
    return (
        <>
         <div className={styles.generalInfoWrapper}>
            <LocalLoading />
            {/* Course Image */}
            <div className={styles.generalImageContainer}>
                {imageUrl ? (
                    <img 
                        src={imageUrl} 
                        alt={title || "Course image"} 
                        className={styles.generalCourseImage}
                    />
                ) : (
                    <div className={styles.generalImagePlaceholder}>
                        <FaImage />
                        <p>Chưa có hình ảnh</p>
                    </div>
                )}
            </div>

            {/* Course Details */}
            <div className={styles.generalInfoDetails}>
                <div className={styles.generalInfoItem}>
                    <strong>Tên khóa học:</strong>
                    <span>{title || "Chưa có tên"}</span>
                </div>
                <div className={styles.generalInfoItem}>
                    <strong>Danh mục:</strong>
                    <span>{category || "Chưa phân loại"}</span>
                </div>
                <div className={styles.generalInfoItem}>
                    <strong>Giá khóa học:</strong>
                    <div className={styles.priceSection}>
                        <span>{price ? `${price.toLocaleString()} VNĐ` : "Miễn phí"}</span>
                        {discount && discount > 0 && (
                            <>
                                <span className={styles.discountBadge}>-{discount}%</span>
                                <span className={styles.discountedPrice}>
                                    {discountedPrice ? `${discountedPrice.toLocaleString()} VNĐ` : "N/A"}
                                </span>
                            </>
                        )}
                    </div>
                </div>
                <div className={styles.generalInfoItem}>
                    <strong>Mô tả:</strong>
                    <span>{description || "Chưa có mô tả"}</span>
                </div>
            </div>
        </div>
        </>
       
    );
}
