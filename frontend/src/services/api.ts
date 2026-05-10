import axios from 'axios';

const API_BASE_URL = 'https://pg-finder-backend-5p03.onrender.com/api/v1';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to attach JWT token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Add a response interceptor to handle unauthorized errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authService = {
  login: (credentials: any) => api.post('/auth/login', credentials),
  register: (userData: any) => api.post('/auth/register', userData),
  getProfile: () => api.get('/profile'),
};

export const pgService = {
  getAllPgs: () => api.get('/pgs'),
  getPgById: (id: string | number) => api.get(`/pgs/${id}`),
  getFullPgById: (id: string | number) => api.get(`/pgs/${id}/full`),
  searchPgs: (params: any) => api.get('/pgs/search', { params }),
};

export const ownerService = {
  getMyPgs: () => api.get('/owners/me/pgs'),
  createPg: (data: any) => api.post('/pgs', data),
  updatePg: (id: string | number, data: any) => api.put(`/pgs/${id}`, data),
  deletePg: (id: string | number) => api.delete(`/pgs/${id}`),
};

export default api;
