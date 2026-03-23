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

export function saveConditions(courseId, conditions) {
    const token = localStorage.getItem("token");
    console.log(token);
    return fetch(`${BASE_URL}/${courseId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(conditions)
    }).then(response => {
        // if (!response.ok) {
        //     throw new Error('Network response was not ok');
        // }
        return response.json();
    });
}