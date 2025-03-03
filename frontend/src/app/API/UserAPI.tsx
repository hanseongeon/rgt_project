import { getAPI } from './AxiosAPI';

export const UserApi = getAPI();

interface orderRequsetDTO{
    name: String;
    count: Number;
  }

  export const sendOrder = async (data: orderRequsetDTO) => {
    const response = await UserApi.post('/api/order', data);
    return response.data;
};