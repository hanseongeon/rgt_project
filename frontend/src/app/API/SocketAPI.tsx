"use client";
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

export interface Subscribe {
    location: string,
    active: (r: string) => void,
}
export function getSocket(subs: Subscribe[]) {
    const Socket = new Client({
        webSocketFactory: () => {
            return new SockJS("https://hserrgtproject.shop/api/ws-stomp");
        },     
        onWebSocketError: () => {
            window.location.reload();
        },
        reconnectDelay: 50000, // 자동 재연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
            subs.forEach(sub => {
                Socket.subscribe(sub.location, (e) => sub.active(JSON.parse(e.body)));
            })
            const interval = setInterval(() => {clearInterval(interval); }, 100);
        }
    });
    Socket.activate();
    return Socket;
}
