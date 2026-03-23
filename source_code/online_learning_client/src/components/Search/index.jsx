import { useState } from "react";
import { useDispatch } from "react-redux";
import { search as s } from "actions/search";
import Button from "components/Button";
import "components/Search/Search.scss";

export default function Search() {
    const [inputValue, setInputValue] = useState("");
    const dispatch = useDispatch();

    const handleSearch = (e) => {
        e.preventDefault();
        const query = inputValue.trim().toLowerCase();
        dispatch(s(query));
        console.log("Search query:", query);
    };

    return (
        <form className="w-100 form-search" onSubmit={handleSearch}>
            <div className="row">
                <div className="col-12 align-items-center d-flex justify-content-center">
                    <input
                        type="text"
                        placeholder="Nhập từ khóa tìm kiếm"
                        value={inputValue}
                        onChange={(e) => setInputValue(e.target.value)}
                    />
                    <Button content="Tìm kiếm" />
                </div>
            </div>
        </form>
    );
}
