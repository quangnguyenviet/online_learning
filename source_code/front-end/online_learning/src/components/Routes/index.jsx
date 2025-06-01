import LayoutDefault from "../../layout/StudentLayout";
import About from "../../pages/student/About";
import Contact from "../../pages/student/Contact";
import Error404 from "../../pages/Error404";
import Home from "../../pages/student/Home";
import InforUser from "../../pages/student/InforUser";
import Login from "../../pages/Login";
import PrivateRouter from "../PrivateRouter";
import CourseDetail from "../../pages/student/CourseDetail";
import MyLearning from "../../pages/student/MyLearning";
import MyLearningDetail from "../../pages/student/MyLearning/MyLearningDetail";
import UploadVideo from "../../pages/student/UploadVideo";
import StudentLayout from "../../layout/StudentLayout";
import InstructorLayout from "../../layout/InstructorLayout";
import Dashboard from "../../pages/instructor/Dashboard";
import InstructorRouter from "../InstructorRouter";
import Courses from "../../pages/instructor/Courses";
import CourseList from "../../pages/instructor/Courses/CourseList/index.jsx";
import AddCourse from "pages/instructor/Courses/AddCourse";
import { EditCourse } from "pages/instructor/Courses/EditCourse";
import ViewDetail from "pages/instructor/Courses/CourseList/ViewDetail";
import Profile from "pages/instructor/Profile";
import Notification from "pages/instructor/Notification";
import NotificationDetail from "pages/instructor/Notification/NotificationDetail";
import InstructorPayment from "pages/admin/InstructorPayment";
import AdminLayout from "layout/AdminLayout";
import { User } from "pages/admin/User";
import AdminLogin from "pages/admin/AdminLogin";
import Signup from "pages/student/SignUp";
import SignupInstructor from "pages/instructor/SignUp";
import { Logout } from "components/Logout";
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
                                // element: <EditCourse />
                                element : <ViewDetail />
                            },
                            {
                                path: "add-new",
                                element: <AddCourse />
                            }
                        ]
                    },
                    {
                        path: "profile",
                        element: <Profile />
                    },
                    {
                        path: "notifications",
                        element: <Notification />,
                        children: [
                            {
                                path: ":id",
                                element : <NotificationDetail />
                            }
                        ]
                    }
                ]
            }
              
            
        ]
    },
    {
        path: "/admin",
        element: <AdminLayout />,
        children: [
            
            {
                path: "users",
                element: <User />
            },
            {
                path: "payments",
                element: <InstructorPayment />
            }
        ]
    },
    {
        path: "/admin/login",
        element: <AdminLogin />
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
        path: "/logout",
        element: <Logout />
    },
    {
        path: "/signup",
        // element: <Signup />
        children: [
            {
                path : "student",
                element: <Signup />
            },
            {
                path: "instructor",
                element: <SignupInstructor />
            }
        ]
    }
];
export default ROUTES;