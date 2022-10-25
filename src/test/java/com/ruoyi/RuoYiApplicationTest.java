package com.ruoyi;

import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.datasync.domain.BinlogEndPos;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.mapper.BinlogEndPosMapper;
import com.ruoyi.datasync.mapper.DsNodeCoreConfigMapper;
import com.ruoyi.datasync.mapper.DsNodeDbConfigMapper;
import com.ruoyi.datasync.service.IBinlogEndPosService;
import com.ruoyi.framework.web.domain.server.Sys;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.List;

@SpringBootTest
public class RuoYiApplicationTest {

    @Autowired
    private DsNodeCoreConfigMapper dsNodeCoreConfigMapper;

    @Autowired
    private IBinlogEndPosService binlogEndPosService;

    @Autowired
    private BinlogEndPosMapper binlogEndPosMapper;

    @Test
    public void test(){
        URL resource = this.getClass().getResource("");
        String property = System.getProperty("user.dir");
        System.out.println("resource:"+resource);
        System.out.println("property:"+property);
        String[] split = property.split(":");
        String str = split[0];
        System.out.println("切割后获取的盘符："+str);
    }

    @Test
    public void test2(){
        BinlogEndPos binlogEndPos = new BinlogEndPos();
        binlogEndPos.setFileName("ceshishushuju");
        binlogEndPos.setEndPos(1000);
        binlogEndPosMapper.insertBinlogEndPos(binlogEndPos);
    }
}
