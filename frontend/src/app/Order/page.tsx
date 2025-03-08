'use client'
import { use, useEffect, useState } from "react"
import { sendOrder } from "../API/UserAPI";
import { getSocket } from "../API/SocketAPI";


export default function Order() {
  interface orderRequsetDTO {
    name: string;
    count: number;
  }
  const [menu, setMenu] = useState<string>("");
  const [count, setCount] = useState<number>(0);
  const [error, setError] = useState<string>("");
  const [socket, setSocket] = useState(null as any);
  const [time,setTime] = useState<Date>();

  useEffect(() => {
    const newSocket = getSocket([]);
        newSocket.onConnect = () => {
            setSocket(newSocket);
        }
  },[])

  useEffect(() => {
    if (!socket) return; // socket이 초기화되지 않았다면 구독하지 않음

    socket.activate();

    return () => {
      console.log("소켓 구독 해제");
      socket.deactivate();
  };

  },[socket])

  const getKoreanDate = (): Date => {
    const date = new Date();
    const offset = 9 * 60;
    const koreanDate = new Date(date.getTime() + offset * 60 * 1000);
    return koreanDate;
};


  const send = (menu: string, count: number) => {
    if (menu.length == 0) {
      setError("메뉴를 입력해주세요")
    } else if (count <= 0) {
      setError("수량을 입력해주세요")
    } else {
      sendOrder({ name: menu, count: Number(count), time :getKoreanDate()}).then(r => { alert("주문성공"); socket.publish({
        destination: "/api/pub/orderList",
        body: JSON.stringify({name: menu, count: count, time: getKoreanDate(), status:0, index:r })
      }); console.log("주문보냄"); window.location.href = "/"; }).catch(e => setError(e));
    }
  }

  return (
    <div className="flex flex-col items-center mt-100">
      <div className="w-[300px] border-black">
        <input type="text" placeholder="메뉴" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full
     p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={(e) => {
            setMenu(e.target.value);
          }} />
        {error?.length == 0 ? <></> : <p className="text-red-300 text-sm">{error}</p>}
        <input type="number" min="0" placeholder="수량" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full
     p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" onChange={(e) => {
            setCount(Number(e.target.value));
          }} />
        <button className="bg-gray-500 hover:bg-gray-400 text-white font-bold py-2 px-4 rounded-full ml-24 " onClick={() => {send(menu, count)}}>주문하기</button>
      </div>
      <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2
         px-4 border border-blue-500 hover:border-transparent rounded mt-5" onClick={() => window.location.href = "/"}>
                    메인페이지로
                </button>
    </div>
  );
}
