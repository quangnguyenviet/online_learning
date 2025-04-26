const BASE_URL = "http://localhost:8080/online_learning/lessons";


export function getSignedUrl(fileName) {
    return fetch(`${BASE_URL}/signed-url`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ fileName }),
    }).then((response) => response.json());
}