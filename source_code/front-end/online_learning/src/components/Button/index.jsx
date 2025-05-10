// Button.js
import "components/Button/Button.scss";

export default function Button({ content, onClick }) {
    return (
        <button className="button" onClick={onClick}>
            {content}
        </button>
    );
}
