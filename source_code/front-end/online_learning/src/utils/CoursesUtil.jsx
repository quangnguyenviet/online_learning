const BASE_URL = "http://localhost:8080/online_learning/courses";

const token = localStorage.getItem("token");

export function getCourseOfInstructor(intructorId){
    return fetch(`${BASE_URL}/instructor/${intructorId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',

        }
    }).then(response => response.json());
}

