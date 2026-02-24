import LayoutDefault from "../../layout/StudentLayout";
import About from "../../pages/student/About";
import Contact from "../../pages/student/Contact";
import Error404 from "../../pages/common/Error404";
import Home from "../../pages/student/Home/Home";
import InforUser from "../../pages/student/InforUser/InforUser";
import Login from "../../pages/common/Login/Login";
import PrivateRouter from "../PrivateRouter";
import CourseDetail from "../../pages/student/CourseDetail/CourseDetail";
import MyLearning from "../../pages/student/MyLearning/MyLearning";
import MyLearningDetail from "../../pages/student/MyLearning/MyLearningDetail";
import UploadVideo from "../../pages/student/UploadVideo";
import StudentLayout from "../../layout/StudentLayout";
import InstructorLayout from "../../layout/InstructorLayout";
import Dashboard from "../../pages/instructor/Dashboard/Dashboard";
import * as Guard from "service/Guard";
import Courses from "../../pages/instructor/Courses";
import CourseList from "../../pages/instructor/Courses/CourseList/CourseList.jsx";
import ViewDetail from "pages/instructor/Courses/ViewDetail/ViewDetail";
import Profile from "pages/instructor/Profile";
import Notification from "pages/instructor/Notification";
import NotificationDetail from "pages/instructor/Notification/NotificationDetail";
import InstructorPayment from "pages/admin/InstructorPayment";
import AdminLayout from "layout/AdminLayout";
import { User } from "pages/admin/User";
import AdminLogin from "pages/admin/AdminLogin";
import Signup from "pages/student/SignUp";
import SignupInstructor from "pages/instructor/SignUp/SignUp";
import Logout from "components/Logout";
import AddNewCourse from "pages/instructor/Courses/AddNewCourse/AddNewCourse";
const ROUTES = [
    {
        element: <StudentLayout />,
        children: [
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
            // {
            //     path: "/upload-video",
            //     element: <UploadVideo />
            // },
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
        element: <Guard.InstructorRoute element={<InstructorLayout />} />,
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
                        element: <ViewDetail />
                    },
                    {
                        path: "add-new",
                        element: <AddNewCourse />
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
                        element: <NotificationDetail />
                    }
                ]
            }
        ]


    },
    {
        path: "/admin",
        element: <Guard.AdminRoute element={<AdminLayout />} />,
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
                path: "student",
                element: <Signup />
            }
        ]
    }
];
export default ROUTES;