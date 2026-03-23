const BASE_URL = `${process.env.REACT_APP_API_URL}/courses`;

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

export function getCourses(payload){
    return fetch(`${BASE_URL}?type=${payload.type}&query=${payload.query}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => response.json());
}
