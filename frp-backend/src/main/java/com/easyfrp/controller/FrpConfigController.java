package com.easyfrp.controller;

import com.easyfrp.model.FrpProxy;
import com.easyfrp.service.FrpConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class FrpConfigController {

    private final FrpConfigService configService;

    public FrpConfigController(FrpConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/list")
    public List<FrpProxy> listConfigs() {
        return configService.listAllProxies();
    }
}
