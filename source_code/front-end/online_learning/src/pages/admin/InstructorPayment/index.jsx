import React, { useState, useEffect } from "react";
import "./InstructorPayment.scss";
import { getPaymentData } from "utils/AdminUtil/InstructorPaymentUtil";
import { update } from "utils/AdminUtil/InstructorPaymentUtil";

const months = Array.from({ length: 12 }, (_, i) => i + 1);
const years = [2025, 2024, 2023];

export default function InstructorPayment() {
  const now = new Date();
  const [filterMonth, setFilterMonth] = useState(now.getMonth() + 1);
  const [filterYear, setFilterYear] = useState(now.getFullYear());
  const [data, setData] = useState([]);
  const [selected, setSelected] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getPaymentData({ month: filterMonth, year: filterYear })
      .then((res) => setData(res || []))
      .catch(() => setData([]))
      .finally(() => setLoading(false));
  }, [filterMonth, filterYear]);

  const handleMarkPaidDirect = (row) => {
    const paidAt = new Date().toISOString().slice(0, 19);
    update({ id: row.id, paidAt })
      .then(res => {
        setData(prev =>
          prev.map(r =>
            r.id === row.id ? { ...r, paidAt } : r
          )
        );
      });
  };

  const handleMarkPaid = () => {
    if (!selected) return;
    console.log("Đánh dấu đã thanh toán cho:", selected);
    const now = new Date();
    const paidAt = now.toISOString().slice(0, 19); // "2025-06-01T12:34:56"
    let params = {
      id: selected.id,
      paidAt: paidAt
    }
    update(params)
      .then(res => {
        console.log("Cập nhật thành công:", res);
      })


    setData((prev) =>
      prev.map((row) =>
        row.id === selected.id
          ? { ...row, paidAt: new Date().toISOString().slice(0, 10) }
          : row
      )
    );
    setSelected(null);
  };

  return (
    <div className="instructor-payment-page">
      <h2 className="page-title">
        <i className="fas fa-money-check-alt"></i> Quản lý thanh toán giáo viên
      </h2>
      <form className="filter-row">
        <label>
          <span>Tháng:</span>
          <select
            value={filterMonth}
            onChange={(e) => setFilterMonth(Number(e.target.value))}
          >
            {months.map((m) => (
              <option key={m} value={m}>{m}</option>
            ))}
          </select>
        </label>
        <label>
          <span>Năm:</span>
          <select
            value={filterYear}
            onChange={(e) => setFilterYear(Number(e.target.value))}
          >
            {years.map((y) => (
              <option key={y} value={y}>{y}</option>
            ))}
          </select>
        </label>
      </form>
      <div className="table-responsive">
        <table className="styled-table">
          <thead>
            <tr>
              <th>STT</th>
              <th>Giáo viên</th>
              <th>Email</th>
              <th>Tổng doanh thu</th>
              <th>Trạng thái</th>
              <th>Thao tác</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={8} style={{ textAlign: "center", color: "#888" }}>
                  Đang tải dữ liệu...
                </td>
              </tr>
            ) : data.length === 0 ? (
              <tr>
                <td colSpan={8} style={{ textAlign: "center", color: "#888" }}>
                  Không có dữ liệu phù hợp
                </td>
              </tr>
            ) : (
              data.map((row, idx) => (
                <tr key={row.id}>
                  <td>{idx + 1}</td>
                  <td>
                    <div className="teacher-info">
                      <i className="fas fa-user"></i>
                      <span>{row.fullName || row.username || row.email}</span>
                    </div>
                  </td>
                  <td>{row.instructor.email}</td>
                  <td>{row.statistic?.totalEarnings?.toLocaleString() || 0} đ</td>
                  <td>
                    {row.paidAt ? (
                      <span className="status paid">
                        <i className="fas fa-check-circle"></i> Đã thanh toán<br />
                        <small>{row.paidAt}</small>
                      </span>
                    ) : (
                      <span className="status unpaid">
                        <i className="fas fa-clock"></i> Chưa thanh toán
                      </span>
                    )}
                  </td>
                  <td>
                    <button
                      className="btn btn-info"
                      onClick={() => setSelected(row)}
                      style={{ marginRight: 8 }}
                    >
                      <i className="fas fa-eye"></i> Xem thông tin
                    </button>
                    {!row.paidAt && (
                      <button
                        className="btn btn-primary"
                        onClick={() => handleMarkPaidDirect(row)}
                      >
                        <i className="fas fa-check"></i> Đánh dấu đã thanh toán
                      </button>
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      {/* Modal xem thông tin và thanh toán */}
      {selected && (
        <div className="payment-modal-backdrop">
          <div className="payment-modal">
            <h3>
              <i className="fas fa-info-circle"></i> Thông tin thanh toán
            </h3>
            <div className="payment-modal-content">
              <p><b>Giáo viên:</b> {selected.fullName || selected.username || selected.email}</p>
              <p><b>Email:</b> {selected.instructor.email}</p>
              <p><b>Ngân hàng:</b> {selected.instructor.bankName || "Chưa cập nhật"}</p>
              <p><b>Số tài khoản:</b> {selected.instructor.accountNumber || "Chưa cập nhật"}</p>
              <p><b>Chủ tài khoản:</b> {selected.instructor.accountName || "Chưa cập nhật"}</p>
              <p><b>Tháng/Năm:</b> {selected.statistic?.month}/{selected.statistic?.year}</p>
              <p><b>Tổng doanh thu:</b> {selected.statistic?.totalEarnings?.toLocaleString() || 0} đ</p>
              <p>
                <b>Trạng thái:</b>{" "}
                {selected.paidAt ? (
                  <span className="status paid">Đã thanh toán ({selected.paidAt})</span>
                ) : (
                  <span className="status unpaid">Chưa thanh toán</span>
                )}
              </p>
            </div>
            <div className="payment-modal-actions">
              {!selected.paidAt && (
                <button className="btn btn-primary" onClick={handleMarkPaid}>
                  <i className="fas fa-check"></i> Đánh dấu đã thanh toán
                </button>
              )}
              <button
                className="btn"
                onClick={() => setSelected(null)}
              >
                Đóng
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}