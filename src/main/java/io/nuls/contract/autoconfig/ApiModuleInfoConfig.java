package io.nuls.contract.autoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiModuleInfoConfig  {

    @Value("${nuls.api.module.service.ip}")
    private String apiModuleApi;

    @Value("${nuls.api.module.service.port}")
    private String apiModulePort;


    public String getApiModuleApi() {
        return apiModuleApi;
    }

    public void setApiModuleApi(String apiModuleApi) {
        this.apiModuleApi = apiModuleApi;
    }

    public String getApiModulePort() {
        return apiModulePort;
    }

    public void setApiModulePort(String apiModulePort) {
        this.apiModulePort = apiModulePort;
    }

    public  String  getApiModuleAddress() {
        String url = "http://" + apiModuleApi + ":" + apiModulePort;
        return url;
    }

}
