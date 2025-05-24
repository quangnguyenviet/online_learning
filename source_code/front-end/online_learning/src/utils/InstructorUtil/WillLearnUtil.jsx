const BASE_URL = "http://localhost:8080/online_learning/instructor-learn-what";





export function saveWillLearn(courseId, willLearns) {
    const token = localStorage.getItem("token");
    return fetch(`${BASE_URL}/${courseId}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(willLearns)
    }).then(response => {
        // if (!response.ok) {
        //     throw new Error('Network response was not ok');
        // }
        return response.json();
    });
}