import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import styles from './InforUser.module.scss';
import modalStyles from './EditUserInfoModal.module.scss';
import { GenderEnum, GenderDisplayNames, getGenderOptions, convertToGenderEnum, convertToVietnamese } from '../../../enums/GenderEnum';

export default function EditUserInfoModal({ isOpen, onClose, userInfo, onSave }) {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        phoneNumber: '',
        dob: '',
        gender: ''
    });

    useEffect(() => {
        if (isOpen && userInfo) {
            const fullName = userInfo.fullName || '';
            const nameParts = fullName.trim().split(' ');
            const firstName = nameParts[0] || '';
            const lastName = nameParts.slice(1).join(' ') || '';

            setFormData({
                firstName: firstName,
                lastName: lastName,
                phoneNumber: userInfo.phoneNumber || '',
                dob: userInfo.dob || '',
                gender: userInfo.gender || ''
            });
        }
    }, [isOpen, userInfo]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSave = () => {
        onSave(formData);
    };

    if (!isOpen) return null;

    return (
        <div className={modalStyles['modal-overlay']}>
            <div className={modalStyles['modal-content']}>
                <div className={modalStyles['modal-header']}>
                    <h2>Chỉnh Sửa Thông Tin Cá Nhân</h2>
                    <button 
                        className={modalStyles['modal-close']} 
                        onClick={onClose}
                    >
                        ×
                    </button>
                </div>

                <div className={modalStyles['modal-body']}>
                    <div className={modalStyles['form-group']}>
                        <label htmlFor="firstName" className={modalStyles['form-label']}>
                            Tên
                        </label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            className={modalStyles['form-input']}
                            placeholder="Nhập tên"
                        />
                    </div>

                    <div className={modalStyles['form-group']}>
                        <label htmlFor="lastName" className={modalStyles['form-label']}>
                            Họ
                        </label>
                        <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            className={modalStyles['form-input']}
                            placeholder="Nhập họ"
                        />
                    </div>

                    <div className={modalStyles['form-group']}>
                        <label htmlFor="phoneNumber" className={modalStyles['form-label']}>
                            Số điện thoại
                        </label>
                        <input
                            type="tel"
                            id="phoneNumber"
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            className={modalStyles['form-input']}
                            placeholder="Nhập số điện thoại"
                        />
                    </div>

                    <div className={modalStyles['form-group']}>
                        <label htmlFor="dob" className={modalStyles['form-label']}>
                            Ngày sinh
                        </label>
                        <input
                            type="date"
                            id="dob"
                            name="dob"
                            value={formData.dob}
                            onChange={handleChange}
                            className={modalStyles['form-input']}
                        />
                    </div>

                    <div className={modalStyles['form-group']}>
                        <label htmlFor="gender" className={modalStyles['form-label']}>
                            Giới tính
                        </label>
                        <select
                            id="gender"
                            name="gender"
                            value={formData.gender}
                            onChange={handleChange}
                            className={modalStyles['form-input']}
                        >
                            <option value="">-- Chọn giới tính --</option>
                            {getGenderOptions().map(option => (
                                <option key={option.value} value={option.value}>
                                    {option.label}
                                </option>
                            ))}
                        </select>
                    </div>
                </div>

                <div className={modalStyles['modal-footer']}>
                    <button
                        className={`${styles.button} ${styles['button--secondary']}`}
                        onClick={onClose}
                    >
                        Hủy
                    </button>
                    <button
                        className={`${styles.button} ${styles['button--primary']}`}
                        onClick={handleSave}
                    >
                        Lưu
                    </button>
                </div>
            </div>
        </div>
    );;
}

EditUserInfoModal.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    userInfo: PropTypes.shape({
        fullName: PropTypes.string,
        email: PropTypes.string,
        phoneNumber: PropTypes.string,
        dob: PropTypes.string,
        gender: PropTypes.string
    }),
    onSave: PropTypes.func.isRequired
};

EditUserInfoModal.defaultProps = {
    userInfo: {}
};
