import styles from './ErrorDisplay.module.css';
import { useState, useEffect, useCallback } from 'react';
import { memo } from 'react';
import { createPortal } from 'react-dom';

const ErrorDisplayComponent = ({message, onDismiss}) => {
    // Auto-dismiss the error after 5 seconds
    useEffect(() => {
        const timer = setTimeout(() => {
            onDismiss();
        }, 5000);

        // Cleanup: Clear the timer if component unmounts or message changes
        return () => clearTimeout(timer);
    }, [message, onDismiss]);
    if(!message) return null;
  
    return createPortal(
        <div className={styles.wrapper}>
            <div className={styles.card}>
                <span className={styles.message}>{message}</span>
                <div className={styles.progress}></div>
            </div>
        </div>,
        document.body
    );
}
export const ErrorDisplay = memo(ErrorDisplayComponent);

export const useError = () => {
    // State to hold the current error message
    const [errorMessage, setErrorMessage] = useState(null);
    const showError = useCallback((message) => {
        setErrorMessage(message);
    }, []);
    const dismissError = useCallback(() => {
        setErrorMessage(null);
    }, []);
    return {
        // Methods to control the error display
        showError,
        dismissError,
        errorMessage
    };
}
