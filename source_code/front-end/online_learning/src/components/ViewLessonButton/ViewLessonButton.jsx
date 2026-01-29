import React from "react";

export default function ViewLessonButton({ children }) {
    const handleView = () => {
        console.log("ViewLessonButton clicked");
    };

    return React.cloneElement(children, {
        onClick: handleView,
    });
}
