export function getCourseStatistics() {
    const instructorId = localStorage.getItem("id");

    return fetch(`http://localhost:8080/online_learning/statistic-instructor/course?instructorId=${instructorId}`, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
    })
    .then((response) => response.json())
    .then((data) => {
        if (data.status === 1000) {
            return data.data;
        } else {
            return null;
        }
    })
    .catch((error) => {
        console.error("Error:", error);
        return null;
    });
}
