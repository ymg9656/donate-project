package com.daun.donate.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3Config {
    @Bean
    public Admin web3j() {
        Admin web3j = Admin.build(new HttpService("http://localhost:7545")); // url입력 ex) http://localhost:8888
        return web3j;
    }
}
