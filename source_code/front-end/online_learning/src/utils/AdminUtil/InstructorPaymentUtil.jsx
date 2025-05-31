
export function getPaymentData() {
  // This function would typically fetch data from an API
  // For now, we return a static object to simulate the data
  return {
  "05/2025": [
    { id: 1, name: "Nguyễn Văn A", total: 15000000, paid: false },
    { id: 2, name: "Trần Thị B", total: 12000000, paid: true },
  ],
  "04/2025": [
    { id: 1, name: "Nguyễn Văn A", total: 10000000, paid: true },
    { id: 3, name: "Phạm Văn C", total: 8000000, paid: false },
  ],
};
}