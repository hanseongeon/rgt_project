import axios from 'axios';

export function getAPI() {
    const api = axios.create({
        baseURL: 'https://hserrgtproject.shop',
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
    });
    return api;
}
