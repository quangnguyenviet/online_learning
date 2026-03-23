export const search = (key) => {
    return {
        type: "SEARCH",
        payload: {
            key: key
        }
    };
}