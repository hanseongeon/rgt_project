'use client'

import { getSocket } from "@/app/API/SocketAPI";
import { changeOrder, deleteOrder, getOrder } from "@/app/API/UserAPI";
import { Client } from "@stomp/stompjs";
import dayjs from "dayjs";
import { useEffect, useState } from "react"

export default function OrderList() {

    interface orderResponseDTO {
        name: string;
        count: number;
        status: number;
        time: Date;
        index: number;
    }

    const [orderList, setOrderList] = useState<orderResponseDTO[]>([]);
    const [socket, setSocket] = useState<Client | null>(null);

    useEffect(() => {
        getOrder().then(_ => { setOrderList(_); }).catch(e => console.log(e));
    }, [])

    useEffect(() => {
        const newSocket = getSocket([]);
        newSocket.onConnect = () => {
            setSocket(newSocket);
        }
    }, [])

    // 소켓 구독 설정
    useEffect(() => {
        if (!socket) return; // socket이 초기화되지 않았다면 구독하지 않음
        socket.activate();
        const readSub = socket.subscribe("/api/sub/orderList", (e: any) => {
            const newOrder: orderResponseDTO = JSON.parse(e.body).body;
            setOrderList(prevOrderList => [...prevOrderList, newOrder]); //항상 최신 orderList 가져오기
        });

        // 컴포넌트 언마운트 시 구독 해제
        return () => {
            readSub.unsubscribe();
            socket.deactivate();
        };
    }, [socket]); // socket이 변경될 때만 실행

    const status = (num: number) => {
        if (num === 0) {
            return <td className="px-6 py-4">주문 접수 중</td>
        } else if (num === 1) {
            return <td className="px-6 py-4">조리중</td>
        } else {
            return <td className="px-6 py-4">완료</td>
        }
    }

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
                            <th scope="col" className="px-6 py-3 text-center" colSpan={2}>결정</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orderList && orderList.length > 0 ? (
                            orderList.map((r: orderResponseDTO, index: number) => (
                                <tr
                                    key={index}
                                    className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
                                >
                                    <td className="px-6 py-4">{r.name}</td>
                                    <td className="px-6 py-4">{r.count}</td>
                                    <td className="px-6 py-4">{dayjs(r.time?.toString()).format("YYYY-MM-DD HH:mm")}</td>
                                    {status(r.status)}
                                    {r.status === 0 ? <td className="px-6 py-4 cursor-pointer hover:text-red-300" onClick={() => {
                                        changeOrder({ index: r.index, status: 1 }).then(_ => setOrderList(_)).catch(e => console.log(e));
                                    }}>접수 하기</td> : r.status === 1 ? <td className="px-6 py-4 cursor-pointer hover:text-red-300" onClick={() => {
                                        changeOrder({ index: r.index, status: 2 }).then(_ => setOrderList(_)).catch(e => console.log(e));
                                    }}>조리 완료</td> : <></>}
                                    {r.status === 0 ? <td className="px-6 py-4 cursor-pointer hover:text-red-300" onClick={() => deleteOrder(r.index).then(_ => {
                                        getOrder().then(_ => setOrderList(_)).catch(e => console.log(e))
                                    }).catch(e => console.log(e))} >취소하기</td> : <></>}
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan={5} className="text-center py-4">
                                    주문 내역이 없습니다.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
            <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2
         px-4 border border-blue-500 hover:border-transparent rounded mt-5" onClick={() => window.location.href = "/"}>
                    메인페이지로
                </button>
        </div>
    )
}