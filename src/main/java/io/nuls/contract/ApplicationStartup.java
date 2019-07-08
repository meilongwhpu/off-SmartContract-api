package io.nuls.contract;

import io.nuls.contract.autoconfig.ApiModuleInitTool;
import io.nuls.core.log.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        ApiModuleInitTool dataInitTool=contextRefreshedEvent.getApplicationContext().getBean(ApiModuleInitTool.class);
        try {
            Log.info("----------run Application Startup Listener-------------");
            dataInitTool.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
