const BASE_URL = "http://localhost:8080/online_learning/courses";

// const token = localStorage.getItem("token");

export function getCourseOfInstructor(intructorId){
    return fetch(`${BASE_URL}/instructor/${intructorId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',

        }
    }).then(response => response.json());
}

export function getCourseById(courseId){
    return fetch(`${BASE_URL}/${courseId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(response => response.json());
}

export function getCourses(key){
    return fetch(`${BASE_URL}?key=${key}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => response.json());
}
