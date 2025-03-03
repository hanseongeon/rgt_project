'use client'

import { getSocket } from "@/app/API/SocketAPI";
import { useEffect, useState } from "react"
import SockJS from "sockjs-client";

function OrderList(){
    const [socket,setSocket] =useState(null as any);

    useEffect(() =>{
        setSocket(getSocket([]));
    },[])
    return(
        <div>

        </div>
    )
}