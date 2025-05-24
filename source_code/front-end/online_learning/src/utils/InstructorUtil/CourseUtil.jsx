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

export function createNewCourse(data) {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append('image', data.image);
    // xoa image khoi data
    delete data.image;

    formData.append('request', new Blob([JSON.stringify(data)], { type: 'application/json' }));

    return fetch(`${COURSE_URL}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`
        },
        body: formData
    }).then(response => response.json());
}

export function updateCourse(courseId, data){
    const token = localStorage.getItem("token");
    
    console.log(data);

    return fetch(`${COURSE_URL}/${courseId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(data)
    }).then(response => response.json());
}
