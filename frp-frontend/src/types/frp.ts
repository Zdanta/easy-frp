// src/types/frp.ts
export interface FrpProxy {
  name: string;
  type: 'tcp' | 'http' | 'https';
  localIp: string;
  localPort: number;
  remotePort?: number;
  customDomains?: string;
}

export interface FrpProxyForm extends Omit<FrpProxy, 'remotePort'> {
  remotePort?: number | null;
}
