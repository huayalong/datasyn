package com.ruoyi.common.config.listener;

import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.datanetty.client.NettyClient;
import com.ruoyi.datasync.DataSourcePool;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IDsNodeCoreConfigService;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataSourceInitRunner implements ApplicationRunner {

    @Autowired
    public IDsNodeDbConfigService dsNodeDbConfigService;

    @Autowired
    public IDsNodeCoreConfigService dsNodeCoreConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<DsNodeDbConfig> dsNodeDbConfigs = dsNodeDbConfigService.selectAll();
        for (DsNodeDbConfig dsNodeDbConfig : dsNodeDbConfigs) {
            DataSourcePool.initPool(dsNodeDbConfig);
        }
        List<DsNodeCoreConfig> dsNodeCoreConfigs = dsNodeCoreConfigService.selectAll();
        initClient(dsNodeCoreConfigs);
    }

    private void initClient(List<DsNodeCoreConfig> dsNodeCoreConfigs) {
        List<DsNodeCoreConfig> list = dsNodeCoreConfigs.stream().filter(e -> !e.getNodeIp().equals(IpUtils.getHostIp())).collect(Collectors.toList());
        for (DsNodeCoreConfig dsNodeCoreConfig : list) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new NettyClient(dsNodeCoreConfig.getNodeIp(), 8881).connect();//启动netty
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, dsNodeCoreConfig.getCoreName()).start();
        }
    }
}
