package io.nuls.contract.autoconfig;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ContractInfoConfigurer  implements InitializingBean {

    private static PropertiesConfiguration propConfig;

    private static final ContractInfoConfigurer CONFIG = new ContractInfoConfigurer();
    /**
     * 自动保存
     */
    private static boolean autoSave = true;

    private ContractInfoConfigurer(){
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    private static void init(){
        String propertiesFile=System.getProperty("user.dir")+ File.separator+"Contract.properties";
        File properties=new File(propertiesFile);
        try {
            if(!properties.exists()){
                properties.createNewFile();
            }
            propConfig = new PropertiesConfiguration(propertiesFile);
            //自动重新加载 
            propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            //自动保存 
            propConfig.setAutoSave(autoSave);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
