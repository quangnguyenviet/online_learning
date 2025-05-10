import { Outlet } from "react-router-dom"
import Footer from "../../components/Footer"
import Nav from "components/Nav";
import "./style.scss"

export default function StudentLayout() {
    return (
        <>
            <div className="student">

                <Nav />
                <main className="main">
                    <Outlet />
                </main>
                <Footer />
            </div>

        </>
    )
}