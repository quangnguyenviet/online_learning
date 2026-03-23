

export function isRegistered(courseId) {
    return fetch(`http://localhost:8080/online_learning/registers/check?courseId=${courseId}`,
        {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
        }
    )

        .then((response) => response.json())
        .catch((error) => {
            console.error("Error fetching registration status:", error);
            return false; // Default to not registered on error
        });
}