package io.nuls.contract;

import io.nuls.contract.autoconfig.AccountDataInitTool;
import io.nuls.core.log.Log;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        AccountDataInitTool dataInitTool=contextRefreshedEvent.getApplicationContext().getBean(AccountDataInitTool.class);
        try {
            Log.info("----------run Application Startup Listener-------------");
            dataInitTool.initDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
