import "./Profile.scss";

export default function Profile() {
  return (
    <div className="profile-container">
      <h1 className="form-title">Thông tin cá nhân</h1>
      <form className="profile-form">
        <div className="form-group">
          <label htmlFor="fullName">Họ và tên</label>
          <input
            type="text"
            className="form-control"
            id="fullName"
            name="fullName"
            placeholder="Nhập họ và tên"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            placeholder="Nhập email"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="phone">Số điện thoại</label>
          <input
            type="tel"
            className="form-control"
            id="phone"
            name="phone"
            placeholder="Nhập số điện thoại"
            required
          />
        </div>

        <h2 className="payment-title">Thông tin thanh toán</h2>

        <div className="form-group">
          <label htmlFor="accountName">Tên tài khoản ngân hàng</label>
          <input
            type="text"
            className="form-control"
            id="accountName"
            name="accountName"
            placeholder="Nhập tên tài khoản ngân hàng"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="accountNumber">Số tài khoản</label>
          <input
            type="text"
            className="form-control"
            id="accountNumber"
            name="accountNumber"
            placeholder="Nhập số tài khoản"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bankName">Tên ngân hàng</label>
          <input
            type="text"
            className="form-control"
            id="bankName"
            name="bankName"
            placeholder="Nhập tên ngân hàng"
            required
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Cập nhật thông tin
        </button>
      </form>
    </div>
  );
}
