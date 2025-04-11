import LayoutDefault from "../../layout/LayoutDefault";
import About from "../../pages/About";
import Contact from "../../pages/Contact";
import Error404 from "../../pages/Error404";
import Home from "../../pages/Home";
import InforUser from "../../pages/InforUser";
import Login from "../../pages/Login";
import PrivateRouter from "../PrivateRouter";
import Signup from "../../pages/Signup";
import CourseDetail from "../../pages/CourseDetail";
import MyLearning from "../../pages/MyLearning";
import MyLearningDetail from "../../pages/MyLearning/MyLearningDetail";
import UploadVideo from "../../pages/UploadVideo";

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
            },
            {
                path: "/courses/:courseId",
                element: <CourseDetail />
            },
            {
                path: "/upload-video",
                element: <UploadVideo />
            },
            {
                element: <PrivateRouter />,
                children: [
                    {
                        path: "/infor-user",
                        element: <InforUser />
                    },
                    {
                        path: "/my-learning",
                        element: <MyLearning />,
                        
                    },
                    {
                        path: "/my-learning/:courseId",
                        element: <MyLearningDetail />
                    }
                ]
            }

        ],
        
    },
    
    {
        path: "*",
        element: <Error404 />
        
    },
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/signup",
        element: <Signup />
    }
];
export default ROUTES;