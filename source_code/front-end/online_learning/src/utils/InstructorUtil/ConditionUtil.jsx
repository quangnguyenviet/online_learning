const BASE_URL = "http://localhost:8080/online_learning/instructor-require";

// get condition of course by courseId
export function getCourseConditions(courseId) {
    const token = localStorage.getItem("token");
    return fetch(`${BASE_URL}/${courseId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        return response.json();
    }
)


}