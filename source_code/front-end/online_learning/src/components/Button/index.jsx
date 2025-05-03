import "components/Button/Button.scss";

export default function Button(props){
    const {content} = props;
    return(
        <button className="button">{content}</button>
    )

}