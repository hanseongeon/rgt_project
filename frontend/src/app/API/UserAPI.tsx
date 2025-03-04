import { getAPI } from './AxiosAPI';

export const UserApi = getAPI();

interface orderRequsetDTO{
    name: String;
    count: Number;
    time: Date;
  }

  interface orderResponseDTO{
    name : String;
    count: number;
  }

  interface orderChangeRequestDTO{
    index: number;
    status: number;
  }



  export const sendOrder = async (data: orderRequsetDTO) => {
    const response = await UserApi.post('/api/order', data);
    return response.data;
};

export const getOrder = async() => {
  const response = await UserApi.get('/api/order');
  return response.data;
};

export const changeOrder = async(data: orderChangeRequestDTO) => {
  const response = await UserApi.put('/api/order',data);
  return response.data;
};

export const deleteOrder = async(data: number) => {
  const response = await UserApi.delete('/api/order',{
    headers : {
      index: data
    }
  });
  return response.data;
};