package com.ruoyi.datasync.support;

import com.ruoyi.common.constant.CacheKeyConstants;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;

import java.util.List;
import java.util.Optional;

/**
 * 查询中心配置、数据库实例、同步规则配置 工具类
 * 
 * @author qubo
 */
public class DBConfigUtil
{

    private static IDsNodeDbConfigService dbConfigService = SpringUtils.getBean(IDsNodeDbConfigService.class);

    /**
     * 根据ip获取中心配置
     * 
     * @param ip
     */
    public static DsConfigResult getNodeCoreByIp(String ip)
    {
        List<DsConfigResult> configResults = (List<DsConfigResult>) CacheUtils.get(CacheKeyConstants.DATA_SYNC_KEY);
        Optional<DsConfigResult> first = configResults.stream().filter(core -> core.getNodeIp().equals(ip)).findFirst();
        if(first.isPresent()){
            return first.get();
        }
        return null;
    }

    /**
     * 获取ip获取中心下所有数据库实例配置
     *
     * @param ip
     * @return
     */
    public static List<DsNodeDbConfig> getDbInstanceByIp(String ip)
    {
        DsConfigResult core = getNodeCoreByIp(ip);
        if(null != core){
           return core.getDbConfigList();
        }
        return null;
    }

    /**
     * 获取ip获取中心下所有数据同步规则配置
     *
     * @param ip
     * @return
     */
    public static List<DsSameDataDbConfig> getDbSyncConfigByIp(String ip)
    {
        DsConfigResult core = getNodeCoreByIp(ip);
        if(null != core){
            return core.getSameDBSyncConfigList();
        }
        return null;
    }

    /**
     * 根据数据源实例id获取数据库实例
     *
     * @param sourceId
     * @return
     */
    public static DsNodeDbConfig getDbInstanceById(long sourceId)
    {
        DsNodeDbConfig dsNodeDbConfig = dbConfigService.selectDsNodeDbConfigById(sourceId);
        return dsNodeDbConfig;
    }


}
