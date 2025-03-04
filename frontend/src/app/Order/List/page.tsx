'use client'

import { getSocket } from "@/app/API/SocketAPI";
import { getOrder } from "@/app/API/UserAPI";
import { useEffect, useState } from "react"
import SockJS from "sockjs-client";

export default function OrderList() {

    interface orderResponseDTO {
        name: String;
        count: number;
    }

    const [orderList, setOrderList] = useState<orderResponseDTO[]>([]);
    const [socket, setSocket] = useState(null as any);

    useEffect(() => {
        getOrder().then(r => { setOrderList(r); console.log(r); }).catch(e => console.log(e));
    }, [])

    useEffect(() => {
        const newSocket = getSocket([]);
        newSocket.onConnect = () => {
            setSocket(newSocket);
        }
        // setSocket(getSocket([]));
        console.log("소켓연걸");
    }, [])

    // 소켓 구독 설정
    useEffect(() => {
        if (!socket) return; // socket이 초기화되지 않았다면 구독하지 않음

        console.log("소켓 구독 시작");
        socket.activate();
        const readSub = socket.subscribe("/api/sub/orderList", (e: any) => {
            // console.log(JSON.parse(e.body));
            const newOrder: orderResponseDTO = JSON.parse(e.body);
            // const newOrderList: orderResponseDTO[] = [...orderList,newOrder];
            // setOrderList(newOrderList);
            setOrderList((prevOrderList) => [...prevOrderList, newOrder])
        });

        // 컴포넌트 언마운트 시 구독 해제
        return () => {
            console.log("소켓 구독 해제");
            readSub.unsubscribe();
            socket.deactivate();
        };
    }, [socket]); // socket이 변경될 때만 실행


    return (
        <div className="h-screen flex flex-col items-center justify-center">
            <div className="w-[700px] max-h-[500px] overflow-y-auto border border-gray-300 rounded-lg">
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                    <thead className="sticky top-0 bg-gray-50 dark:bg-gray-700 text-xs text-gray-700 uppercase dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">메뉴</th>
                            <th scope="col" className="px-6 py-3">수량</th>
                            <th scope="col" className="px-6 py-3">주문시간</th>
                            <th scope="col" className="px-6 py-3">상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orderList?.map((r: orderResponseDTO, index: number) => (
                            <tr
                                key={index}
                                className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
                            >
                                <td className="px-6 py-4">{r.name}</td>
                                <td className="px-6 py-4">{r.count}</td>
                                <td className="px-6 py-4">2025.03.04</td>
                                <td className="px-6 py-4">주문완료</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )
}