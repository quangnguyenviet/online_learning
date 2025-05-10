const BASE_URL = "http://localhost:8080/online_learning/lessons";


export function getSignedUrl(fileName) {
    const token = localStorage.getItem("token");
    return fetch(`${BASE_URL}/signed-url`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ filename: fileName }),
    }).then((response) => response.json());
}