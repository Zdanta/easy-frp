package com.easyfrp.service;

import com.easyfrp.model.FrpProxy;
import com.easyfrp.util.FrpConfigReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class FrpConfigService {

    @Value("${frp.config-path}")
    private String configPath;

    public List<FrpProxy> listAllProxies() {
        File ini = new File(configPath);
        return FrpConfigReader.parseFrpConfig(ini);
    }
}
