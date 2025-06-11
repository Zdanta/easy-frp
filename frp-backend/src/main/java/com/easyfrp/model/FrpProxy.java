package com.easyfrp.model;

import lombok.Data;

@Data
public class FrpProxy {
    private String name;
    private String type;
    private String localIp;
    private int localPort;
    private int remotePort;
    private String customDomains;
}