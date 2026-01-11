import { Outlet } from "react-router-dom";
import Sidebar from "./Sidebar/Sidebar";
import styles from "./InstructorLayout.module.scss";

export default function InstructorLayout() {
    return (
        <div className={styles.wrapper}>
            <Sidebar />
            <main className={styles.mainContent}>
                <Outlet />
            </main>
        </div>
    );
}
