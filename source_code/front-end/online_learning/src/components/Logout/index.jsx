import { logout } from "utils/AuthUtil";

export function Logout(props) {
    const { redirectUrl } = props;
    logout(redirectUrl);

    return null; // This component does not render anything
}