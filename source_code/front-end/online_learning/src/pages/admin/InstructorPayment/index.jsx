import { useEffect, useState } from "react";
import "./InstructorPayment.scss";
import { getPaymentData } from "utils/AdminUtil/InstructorPaymentUtil";



export default function InstructorPayment() {
  const [selectedMonth, setSelectedMonth] = useState([]);
  const [paymentsData, setPaymentsData] = useState([]);
  const [months, setMonths] = useState([]);

  useEffect(() => {
    setPaymentsData( getPaymentData() );
    setMonths(Object.keys(paymentsData)[0] || "05/2025");
  }, []);

  const handleMarkPaid = (id) => {
    setPaymentsData((prev) => ({
      ...prev,
      [selectedMonth]: prev[selectedMonth].map((item) =>
        item.id === id ? { ...item, paid: true } : item
      ),
    }));
  };

  return (
    <div className="payment-container">
      <div className="payment-header">
        <h2>Quản lý trả tiền giáo viên</h2>
        <select
          value={selectedMonth}
          onChange={(e) => setSelectedMonth(e.target.value)}
        >
          {months.map((month) => (
            <option key={month} value={month}>
              Tháng {month}
            </option>
          ))}
        </select>
      </div>

      <table className="payment-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Giáo viên</th>
            <th>Tháng</th>
            <th>Tổng thu nhập</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {(paymentsData[selectedMonth] || []).map((p, idx) => (
            <tr key={p.id}>
              <td>{idx + 1}</td>
              <td>{p.name}</td>
              <td>{selectedMonth}</td>
              <td>{p.total.toLocaleString()} VND</td>
              <td>
                <span className={p.paid ? "paid" : "unpaid"}>
                  {p.paid ? "Đã trả" : "Chưa trả"}
                </span>
              </td>
              <td>
                {!p.paid && (
                  <button onClick={() => handleMarkPaid(p.id)}>
                    Đánh dấu đã trả
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
