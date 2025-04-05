export function setData(key, value){
    localStorage.setItem(key, JSON.stringify(value));
}
export function getData(key){
    const data = localStorage.getItem(key);
    if(data){
        return JSON.parse(data);
    }
    return null;
}
export function removeData(key){
    localStorage.removeItem(key);
}
export function clearData(){
    localStorage.clear();
}