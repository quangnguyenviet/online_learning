import styles from './InforUser.module.scss';

export default function InforUser() {
    // Static user data
    const userData = {
        name: 'Nguyễn Văn A',
        email: 'nguyenvana@example.com',
        phone: '0123 456 789',
        dateOfBirth: '15/05/1998',
        gender: 'Nam',
        address: '123 Đường ABC, Quận 1, TP.HCM',
        joinDate: '01/01/2024',
        studentId: 'SV2024001',
        stats: {
            coursesEnrolled: 5,
            coursesCompleted: 2,
            totalLearningHours: 120
        },
        enrolledCourses: [
            { id: 1, name: 'React.js Cơ Bản', progress: 75 },
            { id: 2, name: 'Node.js Backend', progress: 45 },
            { id: 3, name: 'JavaScript Nâng Cao', progress: 90 },
            { id: 4, name: 'HTML & CSS', progress: 100 }
        ]
    };

    return (
        <div className={styles['user-info-page']}>
            <div className={styles['user-info-page__header']}>
                <h1>Thông Tin Cá Nhân</h1>
                <p>Quản lý thông tin tài khoản và theo dõi tiến độ học tập của bạn</p>
            </div>

            <div className={styles['user-info-page__container']}>
                {/* Profile Card */}
                <div className={styles['user-profile-card']}>
                    <div className={styles['user-profile-card__avatar-wrapper']}>
                        <div className={styles['avatar-placeholder']}>
                            {userData.name.charAt(0)}
                        </div>
                        <h2>{userData.name}</h2>
                        <span className={styles['user-role']}>Học viên</span>
                    </div>

                    <div className={styles['user-profile-card__stats']}>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Khóa học đã tham gia</span>
                            <span className={styles['stat-value']}>{userData.stats.coursesEnrolled}</span>
                        </div>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Khóa học hoàn thành</span>
                            <span className={styles['stat-value']}>{userData.stats.coursesCompleted}</span>
                        </div>
                        <div className={styles['stat-item']}>
                            <span className={styles['stat-label']}>Tổng giờ học</span>
                            <span className={styles['stat-value']}>{userData.stats.totalLearningHours}h</span>
                        </div>
                    </div>
                </div>

                {/* Details Section */}
                <div className={styles['user-details']}>
                    <div className={styles['user-details__section']}>
                        <h3>Thông Tin Cá Nhân</h3>
                        <div className={styles['user-details__grid']}>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Mã học viên</label>
                                <div className={styles['field-value']}>{userData.studentId}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Họ và tên</label>
                                <div className={styles['field-value']}>{userData.name}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Email</label>
                                <div className={styles['field-value']}>{userData.email}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Số điện thoại</label>
                                <div className={styles['field-value']}>{userData.phone}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Ngày sinh</label>
                                <div className={styles['field-value']}>{userData.dateOfBirth}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Giới tính</label>
                                <div className={styles['field-value']}>{userData.gender}</div>
                            </div>
                            <div className={`${styles['user-details__field']} ${styles['full-width']}`}>
                                <label className={styles['field-label']}>Địa chỉ</label>
                                <div className={styles['field-value']}>{userData.address}</div>
                            </div>
                            <div className={styles['user-details__field']}>
                                <label className={styles['field-label']}>Ngày tham gia</label>
                                <div className={styles['field-value']}>{userData.joinDate}</div>
                            </div>
                        </div>
                    </div>

                    <div className={`${styles['user-details__section']} ${styles['courses-section']}`}>
                        <h3>Khóa Học Của Tôi</h3>
                        <div className={styles['courses-section__list']}>
                            {userData.enrolledCourses.map(course => (
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
                        <button className={`${styles.button} ${styles['button--primary']}`}>
                            Chỉnh sửa thông tin
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}