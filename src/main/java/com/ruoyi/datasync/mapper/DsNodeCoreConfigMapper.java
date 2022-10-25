package com.ruoyi.datasync.mapper;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import org.apache.ibatis.annotations.Update;

/**
 * 节点中心配置Mapper接口
 * 
 * @author qubo
 * @date 2022-10-11
 */
@DataSource(DataSourceType.SLAVE)
public interface DsNodeCoreConfigMapper 
{
    /**
     * 查询节点中心配置
     * 
     * @param id 节点中心配置主键
     * @return 节点中心配置
     */
    public DsNodeCoreConfig selectDsNodeCoreConfigById(Long id);

    /**
     * 查询节点中心配置列表
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 节点中心配置集合
     */
    public List<DsNodeCoreConfig> selectDsNodeCoreConfigList(DsNodeCoreConfig dsNodeCoreConfig);

    /**
     * 新增节点中心配置
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 结果
     */
    public int insertDsNodeCoreConfig(DsNodeCoreConfig dsNodeCoreConfig);

    /**
     * 修改节点中心配置
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 结果
     */
    public int updateDsNodeCoreConfig(DsNodeCoreConfig dsNodeCoreConfig);

    /**
     * 删除节点中心配置
     * 
     * @param id 节点中心配置主键
     * @return 结果
     */
    public int deleteDsNodeCoreConfigById(Long id);

    /**
     * 批量删除节点中心配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDsNodeCoreConfigByIds(String[] ids);


    List<DsConfigResult> selectCoreConfig();

    List<DsNodeCoreConfig> selectAll();

    @Update("UPDATE DS_NODE_CORE_CONFIG SET IS_MASTER = '1' WHERE IS_MASTER = '0';")
    void clearOtherMaster(DsNodeCoreConfig dsNodeCoreConfig);
}
