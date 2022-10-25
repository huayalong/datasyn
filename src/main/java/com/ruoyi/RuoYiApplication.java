package com.ruoyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.ruoyi.datanetty.client.NettyClient;
import com.ruoyi.datanetty.server.NettyServer;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        System.setProperty("spring.devtools.restart.enabled", "false");
        try {
            new Thread(() -> {
                try {
                    new NettyServer(8881).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("=================  多中心数据同步系统启动成功！  ============");
    }
}