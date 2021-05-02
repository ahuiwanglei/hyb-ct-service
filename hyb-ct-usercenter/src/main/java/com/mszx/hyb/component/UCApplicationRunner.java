package com.mszx.hyb.component;

import com.mszx.hyb.sdk.config.CSConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UCApplicationRunner implements ApplicationRunner {

    @Value("${hyb.ct.service.key}")
    private String key;
    @Value("${hyb.ct.service.secret}")
    private String secret;
    @Value("${hyb.ct.service.signpwd}")
    private String signpwd;
    @Value("${hyb.ct.service.umshost}")
    private String umshost;

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        System.out.println("UCApplicationRunner was started!");
        CSConfig.newBuild().host("", umshost, "").key(key).secret(secret).signpwd(signpwd);
    }

}
