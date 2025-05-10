export function search (key){
    return {
        type: "SEARCH",
        payload: {
            key: key
        }
    };
}