/**
 * Gender Enum - Maps gender values to standardized enum format
 */
export const GenderEnum = {
    MALE: 'MALE',
    FEMALE: 'FEMALE',
    OTHER: 'OTHER'
};

/**
 * Display names for gender enum (Vietnamese)
 */
export const GenderDisplayNames = {
    [GenderEnum.MALE]: 'Nam',
    [GenderEnum.FEMALE]: 'Nữ',
    [GenderEnum.OTHER]: 'Khác'
};

/**
 * Reverse mapping for converting Vietnamese display names to enum
 */
export const VietnameseToGenderEnum = {
    'Nam': GenderEnum.MALE,
    'Nữ': GenderEnum.FEMALE,
    'Khác': GenderEnum.OTHER
};

/**
 * Convert Vietnamese gender name to GenderEnum
 * @param {string} vietnameseName - Vietnamese gender name (e.g., 'Nam', 'Nữ', 'Khác')
 * @returns {string} - GenderEnum value (e.g., 'MALE', 'FEMALE', 'OTHER')
 */
export const convertToGenderEnum = (vietnameseName) => {
    return VietnameseToGenderEnum[vietnameseName] || null;
};

/**
 * Convert GenderEnum to Vietnamese display name
 * @param {string} genderEnum - GenderEnum value (e.g., 'MALE', 'FEMALE', 'OTHER')
 * @returns {string} - Vietnamese display name (e.g., 'Nam', 'Nữ', 'Khác')
 */
export const convertToVietnamese = (genderEnum) => {
    return GenderDisplayNames[genderEnum] || '';
};

/**
 * Get all gender options for dropdown
 * @returns {Array} - Array of options with label and value
 */
export const getGenderOptions = () => {
    return [
        { value: GenderEnum.MALE, label: GenderDisplayNames[GenderEnum.MALE] },
        { value: GenderEnum.FEMALE, label: GenderDisplayNames[GenderEnum.FEMALE] },
        { value: GenderEnum.OTHER, label: GenderDisplayNames[GenderEnum.OTHER] }
    ];
};
