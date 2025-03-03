'use client'

export default function Home() {

  return (
    <div className="flex items-center justify-center h-screen gap-10">
        <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2
         px-4 border border-blue-500 hover:border-transparent rounded" onClick={() => window.location.href = "/Order"}>
  주문하기
</button>
<button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2
         px-4 border border-blue-500 hover:border-transparent rounded" onClick={() => window.location.href = "/Order"}>
  주문현황
</button>
    </div>
  );
}
