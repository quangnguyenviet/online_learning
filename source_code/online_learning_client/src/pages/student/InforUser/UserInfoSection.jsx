import PropTypes from 'prop-types';
import styles from './InforUser.module.scss';

export default function UserInfoSection({ userInfo }) {
    const renderValue = (value) => (value === null || value === undefined || value === '' ? '\u00A0' : value);
    const joinDate = userInfo.createdAt
        ? new Date(userInfo.createdAt).toLocaleDateString('vi-VN')
        : '';

    return (
        <div className={styles['user-details__section']}>
            <h3>Thông Tin Cá Nhân</h3>
            <div className={styles['user-details__grid']}>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Mã học viên</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.id)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Họ và tên</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.fullName)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Email</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.email)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Số điện thoại</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.phoneNumber)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Ngày sinh</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.dob)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Giới tính</label>
                    <div className={styles['field-value']}>{renderValue(userInfo.gender)}</div>
                </div>
                <div className={styles['user-details__field']}>
                    <label className={styles['field-label']}>Ngày tham gia</label>
                    <div className={styles['field-value']}>{renderValue(joinDate)}</div>
                </div>
            </div>
        </div>
    );
}

UserInfoSection.propTypes = {
    userInfo: PropTypes.shape({
        id: PropTypes.string,
        fullName: PropTypes.string,
        email: PropTypes.string,
        phoneNumber: PropTypes.string,
        dob: PropTypes.string,
        gender: PropTypes.string,
        createdAt: PropTypes.string
    }).isRequired
};
