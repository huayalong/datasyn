package com.ruoyi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.SysConfig;

/**
 * 参数配置 信息操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/dblog")
public class DbLogController extends BaseController{

    /**
     * 跳到首页
     */
    @GetMapping("/index")
    public String index(SysConfig config)
    {
        return "system/onmessage/main";
    }
    
    /**
     * 跳到首页
     */
    @GetMapping("/monitor")
    public String monitor(SysConfig config)
    {
        return "system/onmessage/network";
    }
    
    /**
     * 跳到首页
     */
    @GetMapping("/data")
    @ResponseBody
    public String data()
    {
    	String json = "{    \"topo\": {        \"nodes\": [            {\"knmp_on\": false, \"snmp_on\": true, \"ip\": \"192.168.1.101\", \"vlan\": 1, \"flag\": 0, \"netmask\": \"255.255.255.0\", \"device_name\": \"北京节点\", \"mac\": \"00-11-22-33-44-55\", \"ip_on\": false, \"id\": \"s2\", \"gateway\": \"0.0.0.0\"},             {\"knmp_on\": true, \"snmp_on\": true, \"ip\": \"192.168.1.102\", \"vlan\": 1, \"flag\": 0, \"netmask\": \"255.255.255.0\", \"device_name\": \"device_1\", \"mac\": \"11-22-33-44-55-66\", \"ip_on\": true, \"id\": \"s1\", \"gateway\": \"0.0.0.0\"},            {\"knmp_on\": true, \"snmp_on\": false, \"ip\": \"192.168.1.103\", \"vlan\": 1, \"flag\": 0, \"netmask\": \"255.255.255.0\", \"device_name\": \"device_3\", \"mac\": \"22-33-44-55-66-77\", \"ip_on\": true, \"id\": \"s3\", \"gateway\": \"0.0.0.0\"},            {\"knmp_on\": false, \"snmp_on\": false, \"ip\": \"192.168.1.104\", \"vlan\": 1, \"flag\": 0, \"netmask\": \"255.255.255.0\", \"device_name\": \"device_4\", \"mac\": \"44-55-66-77-88-99\", \"ip_on\": false, \"id\": \"s4\", \"gateway\": \"0.0.0.0\"},            {\"knmp_on\": true, \"snmp_on\": true, \"ip\": \"192.168.1.105\", \"vlan\": 1, \"flag\": 0, \"netmask\": \"255.255.255.0\", \"device_name\": \"西安节点\", \"mac\": \"55-66-77-88-99-00\", \"ip_on\": true, \"id\": \"s0\", \"gateway\": \"0.0.0.0\"}],        \"links\": [            {\"source\": \"s1\", \"target_port_disp\": \"port_3\", \"source_port_disp\": \"port_4\", \"target\": \"s4\"},            {\"source\": \"s3\", \"target_port_disp\": \"port_13\", \"source_port_disp\": \"port_20\", \"target\": \"s4\"},            {\"source\": \"s2\", \"target_port_disp\": \"port_10\", \"source_port_disp\": \"port_13\", \"target\": \"s3\"},            {\"source\": \"s1\", \"target_port_disp\": \"port_4\", \"source_port_disp\": \"port_3\", \"target\": \"s2\"},            {\"source\": \"s2\", \"target_port_disp\": \"port_22\", \"source_port_disp\": \"port_1\", \"target\": \"s3\"},            {\"source\": \"s1\", \"target_port_disp\": \"port_7\", \"source_port_disp\": \"port_9\", \"target\": \"s2\"},            {\"source\": \"s1\", \"target_port_disp\": \"port_1\", \"source_port_disp\": \"port_2\", \"target\": \"s4\"},            {\"source\": \"s3\", \"target_port_disp\": \"port_5\", \"source_port_disp\": \"port_6\", \"target\": \"s4\"}        ]    }}";
    	return json;
    }
	
}
