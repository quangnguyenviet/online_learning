import LayoutDefault from "../../layout/LayoutDefault";
import About from "../../pages/About";
import Contact from "../../pages/Contact";
import Error404 from "../../pages/Error404";
import Home from "../../pages/Home";

const ROUTES = [
    {
        element: <LayoutDefault />,
        children : [
            {
                path: "/",
                element: <Home />
            },
            {
                path: "/about",
                element: <About />
            },
            {
                path: "/contact",
                element: <Contact />
            }
        ],
        
    },
    {
        path: "*",
        element: <Error404 />
        
    }
];
export default ROUTES;