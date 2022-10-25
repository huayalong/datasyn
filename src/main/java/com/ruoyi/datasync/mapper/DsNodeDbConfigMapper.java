package com.ruoyi.datasync.mapper;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 节点中心数据源配置Mapper接口
 * 
 * @author qubo
 * @date 2022-10-11
 */
@DataSource(DataSourceType.SLAVE)
public interface DsNodeDbConfigMapper 
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
     * 删除节点中心数据源配置
     * 
     * @param id 节点中心数据源配置主键
     * @return 结果
     */
    public int deleteDsNodeDbConfigById(Long id);

    /**
     * 批量删除节点中心数据源配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDsNodeDbConfigByIds(String[] ids);


    /**
     * 查询节点中心数据源配置
     *
     * @param ids 节点中心数据源配置主键
     * @return 节点中心数据源配置
     */
    public List<DsNodeDbConfig> selectDsNodeDbConfigByIds(@Param("ids") List<String> ids);


    /**
     * 查询所有
     * @return
     */
    List<DsNodeDbConfig> selectAll();
}
