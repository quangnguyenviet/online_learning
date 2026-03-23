import React from 'react';
import { FaPlus, FaTimes } from 'react-icons/fa';
import styles from '../AddNewCourse.module.scss';

export default function CourseLearningObjectives({ 
    objectives, 
    objectiveInput, 
    onInputChange, 
    onAdd, 
    onRemove 
}) {
    return (
        <section className={styles.section}>
            <label>Mục Tiêu Học Tập</label>

            <div className={styles.bulletInput}>
                <input
                    type="text"
                    placeholder="Thêm mục tiêu..."
                    value={objectiveInput}
                    onChange={(e) => onInputChange(e.target.value)}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter') {
                            e.preventDefault();
                            onAdd();
                        }
                    }}
                />
                <button type="button" onClick={onAdd}>
                    <FaPlus />
                </button>
            </div>

            <ul className={styles.bulletList}>
                {objectives.map((item, index) => (
                    <li key={index}>
                        {item}
                        <button
                            type="button"
                            onClick={() => onRemove(index)}
                            className={styles.removeBtn}
                        >
                            <FaTimes />
                        </button>
                    </li>
                ))}
            </ul>
        </section>
    );
}
