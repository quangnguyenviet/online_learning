import { useEffect, useState } from 'react';
import styles from './InforUser.module.scss';
import UserInfoSection from './UserInfoSection';
import EditUserInfoModal from './EditUserInfoModal';
import UserApi from 'service/apis/UserApi';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
export default function InforUser() {
    const { ErrorDisplay, showError } = useError();

    // Static user data (split for future APIs)
    const [userStat, setUserStat] = useState({
        name: 'Nguyễn Văn A',
        stats: {
            coursesEnrolled: 5,
            coursesCompleted: 2,
            totalLearningHours: 120
        }
    });

    const [userInfo, setUserInfo] = useState({
        id: null,
        fullName: null,
        email: null,
        phoneNumber: null,
        dob: null,
        gender: null,
        createdAt: null
    });

    const [isEditModalOpen, setIsEditModalOpen] = useState(false);

    const handleEditClick = () => {
        setIsEditModalOpen(true);
        console.log('Edit button clicked');
    };

    const handleCloseEditModal = () => {
        setIsEditModalOpen(false);
    };

    const handleSaveUserInfo = async (formData) => {
        console.log('Saving user info:', formData);
        try {
            // Call API to save updated user info
            const response = await UserApi.updateMyInfo(formData);
            console.log('API response:', response);
            
            // Update local state with new data
            setUserInfo((prev) => ({
                ...prev,
                firstName: formData.firstName,
                lastName: formData.lastName,
                phoneNumber: formData.phoneNumber,
                dob: formData.dob,
                gender: formData.gender,
                fullName: `${formData.firstName} ${formData.lastName}`.trim()
            }));
            
            setUserStat((prev) => ({
                ...prev,
                name: `${formData.firstName} ${formData.lastName}`.trim() || prev.name
            }));
            
            setIsEditModalOpen(false);
        } catch (error) {
            console.error('Error saving user info:', error);
            showError('Không thể cập nhật thông tin. Vui lòng thử lại sau.');
        }
    };

    const enrolledCourses = [
        { id: 1, name: 'React.js Cơ Bản', progress: 75 },
        { id: 2, name: 'Node.js Backend', progress: 45 },
        { id: 3, name: 'JavaScript Nâng Cao', progress: 90 },
        { id: 4, name: 'HTML & CSS', progress: 100 }
    ];

    const fetchUserInfo = async () => {
        try {
            const response = await UserApi.getMyInfo();
            const data = response?.data ?? {};

            setUserStat((prev) => ({
                ...prev,
                name: data.fullName || prev.name
            }));

            setUserInfo((prev) => ({
                ...prev,
                id: data.id ?? prev.id,
                fullName: data.fullName ?? prev.fullName,
                email: data.email ?? prev.email,
                phoneNumber: data.phoneNumber ?? prev.phoneNumber,
                dob: data.dob ?? prev.dob,
                gender: data.gender ?? prev.gender,
                createdAt: data.createdAt ?? prev.createdAt
            }));
        } catch (error) {
            console.error('Error fetching user info:', error);
            showError('Không thể tải thông tin người dùng. Vui lòng thử lại sau.');
        }
    }

    useEffect(() => {
        fetchUserInfo();
    }, []);

    return (
        <div className={styles['user-info-page']}>
            <ErrorDisplay />
            <EditUserInfoModal
                isOpen={isEditModalOpen}
                onClose={handleCloseEditModal}
                userInfo={userInfo}
                onSave={handleSaveUserInfo}
            />
            <div className={styles['user-info-page__header']}>
                <h1>Thông Tin Cá Nhân</h1>
                <p>Quản lý thông tin tài khoản và theo dõi tiến độ học tập của bạn</p>
            </div>

            <div className={styles['user-info-page__container']}>
                {/* Profile Card */}
                <div className={styles['user-profile-card']}>
                    <div className={styles['user-profile-card__avatar-wrapper']}>
                        <div className={styles['avatar-placeholder']}>
                            {userStat.name.charAt(0)}
                        </div>
                        <h2>{userStat.name}</h2>
                        <span className={styles['user-role']}>Học viên</span>
                    </div>

                    <div className={styles['user-profile-card__stats']}>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Khóa học đã tham gia</span>
                            <span className={styles['stat-value']}>{userStat.stats.coursesEnrolled}</span>
                        </div>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Khóa học hoàn thành</span>
                            <span className={styles['stat-value']}>{userStat.stats.coursesCompleted}</span>
                        </div>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Tổng giờ học</span>
                            <span className={styles['stat-value']}>{userStat.stats.totalLearningHours}h</span>
                        </div>
                    </div>
                </div>

                {/* Details Section */}
                <div className={styles['user-details']}>
                    <UserInfoSection userInfo={userInfo} />

                    <div className={`${styles['user-details__section']} ${styles['courses-section']}`}>
                        <h3>Khóa Học Của Tôi</h3>
                        <div className={styles['courses-section__list']}>
                            {enrolledCourses.map(course => (
                                <div key={course.id} className={styles['course-card']}>
                                    <div className={styles['course-card__image']} />
                                    <div className={styles['course-card__content']}>
                                        <h4>{course.name}</h4>
                                        <p className={styles['course-progress']}>
                                            Tiến độ: {course.progress}%
                                        </p>
                                        <div className={styles['progress-bar']}>
                                            <div 
                                                className={styles['progress-bar__fill']} 
                                                style={{ width: `${course.progress}%` }}
                                            />
                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className={styles['user-details__actions']}>
                        <button className={`${styles.button} ${styles['button--secondary']}`}>
                            Đổi mật khẩu
                        </button>
                        <button 
                            className={`${styles.button} ${styles['button--primary']}`}
                            onClick={handleEditClick}
                        >
                            Chỉnh sửa thông tin
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}