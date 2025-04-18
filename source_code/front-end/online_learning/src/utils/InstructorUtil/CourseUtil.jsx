const COURSE_URL = "http://localhost:8080/online_learning/courses-instructor";


// use for instructor to get all courses
export function getMyCourses(){
    const token = localStorage.getItem("token");

    return fetch(`${COURSE_URL}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    }).then(response => response.json());
}

