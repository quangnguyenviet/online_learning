import Button from "components/Button";
import "components/Search/Search.scss";

export default function Search() {
    return (
        <>
            <form className="w-100 form-search">

            <div className="row">
                <div className="col-12 align-items-center d-flex justify-content-center">
                    <input type="text" placeholder="Nhập từ khóa tìm kiếm" />
                    <Button content="Tìm kiếm" />
                </div>
            </div>
            </form>
           
        </>
    );
}