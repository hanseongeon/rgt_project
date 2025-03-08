import axios from 'axios';

export function getAPI() {
    const api = axios.create({
        baseURL: 'http://13.125.86.24:8080',
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
    });
    return api;
}
