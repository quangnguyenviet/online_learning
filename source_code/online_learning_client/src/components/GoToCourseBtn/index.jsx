import { useNavigate } from "react-router-dom";

export default function GoToCourseBtn(props) {
    const { course } = props;
    const courseId = course.id;
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/my-learning/${courseId}`);
    }

    return (
        <button onClick={handleClick} className="btn btn-primary">vao hoc</button>
    );
}