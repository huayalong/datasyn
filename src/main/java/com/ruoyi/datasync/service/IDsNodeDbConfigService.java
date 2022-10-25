package com.ruoyi.datasync.service;

import java.util.List;
import com.ruoyi.datasync.domain.DsNodeDbConfig;

/**
 * 节点中心数据源配置Service接口
 * 
 * @author qubo
 * @date 2022-10-11
 */
public interface IDsNodeDbConfigService 
{
    /**
     * 查询节点中心数据源配置
     * 
     * @param id 节点中心数据源配置主键
     * @return 节点中心数据源配置
     */
    public DsNodeDbConfig selectDsNodeDbConfigById(Long id);

    /**
     * 查询节点中心数据源配置列表
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 节点中心数据源配置集合
     */
    public List<DsNodeDbConfig> selectDsNodeDbConfigList(DsNodeDbConfig dsNodeDbConfig);

    /**
     * 新增节点中心数据源配置
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 结果
     */
    public int insertDsNodeDbConfig(DsNodeDbConfig dsNodeDbConfig);

    /**
     * 修改节点中心数据源配置
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 结果
     */
    public int updateDsNodeDbConfig(DsNodeDbConfig dsNodeDbConfig);

    /**
     * 批量删除节点中心数据源配置
     * 
     * @param ids 需要删除的节点中心数据源配置主键集合
     * @return 结果
     */
    public int deleteDsNodeDbConfigByIds(String ids);

    /**
     * 删除节点中心数据源配置信息
     * 
     * @param id 节点中心数据源配置主键
     * @return 结果
     */
    public int deleteDsNodeDbConfigById(Long id);


    /**
     * 查询节点中心数据源配置
     *
     * @param ids 节点中心数据源配置主键
     * @return 节点中心数据源配置
     */
    public List<DsNodeDbConfig> selectDsNodeDbConfigByIds(List<String> ids);

    /**
     * 获取所有的中心
     * @reuurn
     */
    List<DsNodeDbConfig> selectAll();
}
