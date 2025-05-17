// src/utils/fetchApi.js

export async function authenticate(url, method = 'GET', data = null) {
    const options = {
      method,
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

export function logout() {
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
              window.location.href = '/login';
              
          }
      })
  }
  