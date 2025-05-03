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

export function setPrice(courseId, price) {
    const token = localStorage.getItem("token");

    return fetch(`${COURSE_URL}/price`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ price, courseId })
    }).then(response => response.json());
}

export function createNewCourse(title, price) {
    const token = localStorage.getItem("token");

    return fetch(`${COURSE_URL}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ title, price })
    }).then(response => response.json());
}
