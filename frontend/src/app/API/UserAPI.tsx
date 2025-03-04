import { getAPI } from './AxiosAPI';

export const UserApi = getAPI();

interface orderRequsetDTO{
    name: String;
    count: Number;
  }

  interface orderResponseDTO{
    name : String;
    count: number;
  }



  export const sendOrder = async (data: orderRequsetDTO) => {
    const response = await UserApi.post('/api/order', data);
    return response.data;
};

export const getOrder = async() => {
  const response = await UserApi.get('/api/order');
  return response.data;
};