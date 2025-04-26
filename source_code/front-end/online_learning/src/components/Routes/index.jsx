import LayoutDefault from "../../layout/StudentLayout";
import About from "../../pages/student/About";
import Contact from "../../pages/student/Contact";
import Error404 from "../../pages/Error404";
import Home from "../../pages/student/Home";
import InforUser from "../../pages/student/InforUser";
import Login from "../../pages/Login";
import PrivateRouter from "../PrivateRouter";
import Signup from "../../pages/Signup";
import CourseDetail from "../../pages/student/CourseDetail";
import MyLearning from "../../pages/student/MyLearning";
import MyLearningDetail from "../../pages/student/MyLearning/MyLearningDetail";
import UploadVideo from "../../pages/student/UploadVideo";
import StudentLayout from "../../layout/StudentLayout";
import InstructorLayout from "../../layout/InstructorLayout";
import Dashboard from "../../pages/instructor/Dashboard";
import InstructorRouter from "../InstructorRouter";
import Courses from "../../pages/instructor/Courses";
import { CourseDetail as ICourseDetail } from "../../pages/instructor/Courses/CourseDetail";
import CourseList from "../../pages/instructor/Courses/CourseList";
import AddCourse from "pages/instructor/Courses/AddCourse";

const ROUTES = [
    {
        element: <StudentLayout />,
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
                    },
                    
                ]
            }

        ],
        
    },
    {
        path: "/instructor",
        element: <InstructorLayout />,
        children: [
            {
                element: <InstructorRouter />,
                children: [
                    {
                        path: "dashboard",
                        element: <Dashboard />
                    },
                    {
                        path: "courses",
                        element: <Courses />,
                        children: [
                            {
                                path: "",
                                element: <CourseList />
                                
                            },
                            {
                                path: ":courseId",
                                element: <ICourseDetail />
                            },
                            {
                                path: "add-new",
                                element: <AddCourse />
                            }
                        ]
                    }
                ]
            }
              
            
        ]
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