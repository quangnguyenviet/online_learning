// src/utils/fetchApi.js

// load BASE_URL from environment variables or define it here
const BASE_URL = process.env.REACT_APP_BASE_URL
const URL_SIGNUP = `${BASE_URL}/users`;

// http://localhost:8080/online_learning/auth/login
export async function authenticate(data) {
    const url = `${BASE_URL}/auth/login`;
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    };
  
    if (data) {
      options.body = JSON.stringify(data);
    }
  
    try {
      const response = await fetch(url, options);
      const json = await response.json();
      return json;
    } catch (error) {
      console.error('Fetch API Error:', error);
      throw error;
    }
  }

export function logout(redirectUrl = '/login') {
    const token = localStorage.getItem("token");
    
    fetch('http://localhost:8080/online_learning/auth/logout', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify({ token: token })
  })
      .then((response) => response.json())
      .then((data) => {
          console.log(data);
          if (data.status === 1000) {
              localStorage.removeItem('token');
              localStorage.removeItem("role");
              localStorage.removeItem("id");
              window.location.href = redirectUrl;
              
          }
      })
  }

  export function signup(data = null) {
    return fetch(URL_SIGNUP, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .catch((error) => {
        console.error('Error during signup:', error);
        throw error;
      });
  }


  