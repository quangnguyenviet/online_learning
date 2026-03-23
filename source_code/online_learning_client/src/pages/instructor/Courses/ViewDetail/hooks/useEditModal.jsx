import { useState } from "react";
import CustomModal from "components/CustomModal/CustomModal";
import EditGeneral from "../../EditGeneral/EditGeneral";
import { EditConditions } from "../../EditConditons";
import { LessonList } from "../../LessonList/LessonList";
import { EditObjectives } from "../../EditObjectives/EditObjectives";

const MODAL_TYPES = {
    GENERAL: "general",
    CONDITIONS: "conditions",
    WILL_LEARN: "willLearn",
    LESSON: "lesson"
};

export default function EditModal({ data, setData }) {
    const [showModal, setShowModal] = useState(false);
    const [modalType, setModalType] = useState("");

    const openModal = (type) => {
        setModalType(type);
        setShowModal(true);
    };

    const closeModal = () => {
        setShowModal(false);
        setModalType("");
    };

    const renderModalContent = () => {
        switch (modalType) {
            case MODAL_TYPES.GENERAL:
                return <EditGeneral data={data} />;
            case MODAL_TYPES.CONDITIONS:
                return <EditConditions conditions={data.requires} />;
            case MODAL_TYPES.WILL_LEARN:
                return <EditObjectives id={data.id} objectives={data.objectives} />;
            case MODAL_TYPES.LESSON:
                return <LessonList lessons={data.lessons} />;
            default:
                return null;
        }
    };

    return {
        showModal,
        setShowModal,
        openModal,
        closeModal,
        CustomModal: () => <CustomModal
            isOpen={showModal}
            onClose={closeModal}
            title="Chỉnh sửa khóa học"
        >
            {renderModalContent()}
        </CustomModal>
    };
}
