// src/api/frp.ts
import axios from 'axios';
import type { FrpProxyForm, FrpProxy } from '@/types/frp';

export function fetchFrpProxyList(): Promise<FrpProxy[]> {
  return axios.get<FrpProxy[]>('/api/config/list').then(res => res.data);
}

export function addFrpProxyConfig(data: FrpProxyForm): Promise<string> {
  return axios.post('/api/config/add', data).then(res => res.data);
}
