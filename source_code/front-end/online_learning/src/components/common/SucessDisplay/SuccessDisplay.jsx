import styles from './SuccessDisplay.module.scss';
import { useState, useEffect, useCallback } from 'react';
export const SuccessDisplay = ({message, onDismiss}) => {
    // Auto-dismiss the error after 5 seconds
    useEffect(() => {
        if (!message) return;
        const timer = setTimeout(() => {
            onDismiss();
        }, 5000);

        // Cleanup: Clear the timer if component unmounts or message changes
        return () => clearTimeout(timer);
    }, [message, onDismiss]);
    if(!message) return null;
    return (
        <div className={styles.wrapper}>
            <div className={styles.card}>
                <span className={styles.message}>{message}</span>
                <div className={styles.progress}></div>
            </div>
        </div>
    );
}

export const useSuccess = () => {
    // State to hold the current error message
    const [successMessage, setSuccessMessage] = useState(null);
    const showSuccess = useCallback((message) => {
        setSuccessMessage(message);
    }, []);
    const dismissSuccess = useCallback(() => {
        setSuccessMessage(null);
    }, []);
    return {
        // Component that renders the error display
        SuccessDisplay: () => (
            <SuccessDisplay
                message={successMessage}
                onDismiss={dismissSuccess}
            />
        ),
        successMessage,
        // Methods to control the error display
        showSuccess,
        dismissSuccess
    };
}