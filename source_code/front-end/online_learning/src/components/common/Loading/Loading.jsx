import styles from './Loading.module.scss';
import {useState, useEffect} from 'react';

const GlobalLoading = ({isLoading, message = "Loading..."}) => {
    const [visible, setVisible] = useState(false);

    useEffect(() => {
        let timer;
        if (isLoading) {
            // Show loading indicator after 300ms delay
            timer = setTimeout(() => setVisible(true), 300);
        } else {
            // Hide loading indicator immediately
            setVisible(false);
        }

        return () => clearTimeout(timer);
    }, [isLoading]);

    if (!visible) return null;

    return (
        <div className={styles.globalLoading}>
            <div className={styles.spinner}></div>
            <div className={styles.message}>{message}</div>
        </div>
    );
};

const LocalLoading = ({isLoading, message = "Loading..."}) => {
    if (!isLoading) return null;
    return (
        <div className={styles.localLoading}>
            <div className={styles.spinner}></div>
            <div className={styles.message}>{message}</div>
        </div>
    );
}

export const useGlobalLoading = () => {
    const [isLoading, setIsLoading] = useState(false);
    const showLoading = () => setIsLoading(true);
    const hideLoading = () => setIsLoading(false);
    return {
        GlobalLoading: () => <GlobalLoading isLoading={isLoading} />,
        LocalLoading: () => <LocalLoading isLoading={isLoading} />,
        showLoading,
        hideLoading
    };
}