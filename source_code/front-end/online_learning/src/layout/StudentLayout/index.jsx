import { Outlet } from "react-router-dom"
import Footer from "../../components/Footer"
import styles from './style.module.scss'
import Nav from "../Nav/Nav"

export default function StudentLayout() {
    return (
        <>
            <div className={styles['student']}>

                <Nav />
                <main className={styles['main']}>
                    <Outlet />
                </main>
                <Footer />
            </div>

        </>
    )
}