class StorageService {

    static saveData(key, value) {
        localStorage.setItem(key, JSON.stringify(value));
    }

    static removeData(key) {
        localStorage.removeItem(key);
    }

  
    static saveToken(token) {
        localStorage.setItem('token', token);
    }

    static getToken() {
        return localStorage.getItem('token');
    }

    static removeToken() {
        localStorage.removeItem('token');
    }
    static saveRole(role) {
        localStorage.setItem('role', JSON.stringify(role));
    }
    static removeUserData() {
        this.removeToken();
        this.removeData('role');
    }
}

export default StorageService;
