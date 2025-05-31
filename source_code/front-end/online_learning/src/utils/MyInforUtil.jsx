const BASE_URL = "http://localhost:8080/online_learning/users";

export const getMyInfor = () => {
    const token = localStorage.getItem("token");
    return fetch(`${BASE_URL}/my-info`, {
        method: 'GET',
        headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        return response.json();
    })

    .catch(error => {
        console.error('Error fetching my information:', error);
        throw error;
    });
}