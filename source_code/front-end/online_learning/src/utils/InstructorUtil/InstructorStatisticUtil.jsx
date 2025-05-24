

export function getInstructorStattic() {
    const instructorId = localStorage.getItem("id");
    const token = localStorage.getItem("token");

  return fetch("http://localhost:8080/online_learning/statistic-instructor?instructorId=" + instructorId, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  })
    .then((response) => {
      return response.json();
    })
    .then((data) => {
      return data;
    })
    .catch((error) => {
      console.error("Error fetching instructor statistics:", error);
    });
}