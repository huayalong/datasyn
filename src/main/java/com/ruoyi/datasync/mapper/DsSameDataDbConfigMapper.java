package com.ruoyi.datasync.mapper;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;

/**
 * 同构数据同步配置Mapper接口
 * 
 * @author qubo
 * @date 2022-10-11
 */
@DataSource(DataSourceType.SLAVE)
public interface DsSameDataDbConfigMapper
{
    /**
     * 查询同构数据同步配置
     * 
     * @param id 同构数据同步配置主键
     * @return 同构数据同步配置
     */
    public DsSameDataDbConfig selectDsSameDataDbconfigById(Long id);

    /**
     * 查询同构数据同步配置列表
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 同构数据同步配置集合
     */
    public List<DsSameDataDbConfig> selectDsSameDataDbconfigList(DsSameDataDbConfig dsSameDataDbconfig);

    /**
     * 新增同构数据同步配置
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 结果
     */
    public int insertDsSameDataDbconfig(DsSameDataDbConfig dsSameDataDbconfig);

    /**
     * 修改同构数据同步配置
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 结果
     */
    public int updateDsSameDataDbconfig(DsSameDataDbConfig dsSameDataDbconfig);

    /**
     * 删除同构数据同步配置
     * 
     * @param id 同构数据同步配置主键
     * @return 结果
     */
    public int deleteDsSameDataDbconfigById(Long id);

    /**
     * 批量删除同构数据同步配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDsSameDataDbconfigByIds(String[] ids);
}
