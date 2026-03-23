const BASE_URL = "http://localhost:8080/online_learning/admin-instructor-payment";

const mockData = [
  {
    id: "1",
    instructor: "Nguyễn Văn A",
    month: 4,
    year: 2025,
    total_earnings: 12000000,
    paid_at: "2025-05-05",
  },
  {
    id: "2",
    instructor: "Trần Thị B",
    month: 4,
    year: 2025,
    total_earnings: 8000000,
    paid_at: null,
  },
  {
    id: "3",
    instructor: "Lê Văn C",
    month: 3,
    year: 2025,
    total_earnings: 9500000,
    paid_at: "2025-04-05",
  },
];

export function getPaymentData(params) {
  const token = localStorage.getItem("token");
  const query = params
    ? "?" +
      Object.entries(params)
        .map(([k, v]) => `${encodeURIComponent(k)}=${encodeURIComponent(v)}`)
        .join("&")
    : "";
  return fetch(BASE_URL + query, {
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
    console.log("Payment data fetched successfully:", data);
    return data.data; // Replace with actual data from API
  })
  .catch((error) => {
    console.error("Error fetching payment data:", error);
    return mockData; // Fallback to mock data in case of error
  });

  
}

export function update(params){
  const token = localStorage.getItem("token");
  console.log(params);
  return fetch(BASE_URL, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(params),
  })
  .then((response) => {
    
    return response.json();
  })
  .then((data) => {
    return data;
  })
  .catch((error) => {
    console.error("Error updating payment:", error);
    throw error; // Rethrow error for further handling
  });

}