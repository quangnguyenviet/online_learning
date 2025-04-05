// export default function CourseDetail() {
//     const url = "http://localhost:8080/online_learning/courses/1";

//     const [course, setCourse] = useState({});
//     const [loading, setLoading] = useState(true);

//     useEffect(() => {
//         const fetchCourse = async (url) => {
//             try {
//                 const response = await fetch(url);
//                 const data = await response.json();
//                 // console.log(data);
//                 setCourse(data.data);
//             } catch (error) {
//                 console.error("Error fetching course:", error);
//             } finally {
//                 setLoading(false);
//             }
//         };

//         fetchCourse(url);
//     }, []);

// }