package io.nuls.contract.autoconfig;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiModuleInfoConfig  implements InitializingBean {

    @Value("${nuls.api.module.service.ip}")
    private String apiModuleApi;

    @Value("${nuls.api.module.service.port}")
    private String apiModulePort;

    @Value("${language}")
    private String language;

    @Value("${dataPath}")
    private String dataPath;

    @Value("${keystorePath}")
    private String keystorePath;

    @Value("${log.path}")
    private String logPath;

    @Value("${log.level}")
    private String logLevel;

    @Value("${chainId}")
    private String chainId;

    @Value("${assetId}")
    private String assetId;

    @Value("${assetChainId}")
    private String assetChainId;

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

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public  String  getApiModuleAddress() {
        String url = "http://" + apiModuleApi + ":" + apiModulePort;
        return url;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAssetChainId() {
        return assetChainId;
    }

    public void setAssetChainId(String assetChainId) {
        this.assetChainId = assetChainId;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("log.level",this.getLogLevel());
        System.setProperty("log.path",this.getLogPath());
    }
}
