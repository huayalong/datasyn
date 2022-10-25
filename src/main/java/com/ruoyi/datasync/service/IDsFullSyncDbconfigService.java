package com.ruoyi.datasync.service;

import java.util.List;
import com.ruoyi.datasync.domain.DsFullSyncDbconfig;

/**
 * 全量数据同步配置Service接口
 * 
 * @author qubo
 * @date 2022-10-13
 */
public interface IDsFullSyncDbconfigService 
{
    /**
     * 查询全量数据同步配置
     * 
     * @param ID 全量数据同步配置主键
     * @return 全量数据同步配置
     */
    public DsFullSyncDbconfig selectDsFullSyncDbconfigByID(Long ID);

    /**
     * 查询全量数据同步配置列表
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 全量数据同步配置集合
     */
    public List<DsFullSyncDbconfig> selectDsFullSyncDbconfigList(DsFullSyncDbconfig dsFullSyncDbconfig);

    /**
     * 新增全量数据同步配置
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 结果
     */
    public int insertDsFullSyncDbconfig(DsFullSyncDbconfig dsFullSyncDbconfig);

    /**
     * 修改全量数据同步配置
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 结果
     */
    public int updateDsFullSyncDbconfig(DsFullSyncDbconfig dsFullSyncDbconfig);

    /**
     * 批量删除全量数据同步配置
     * 
     * @param IDs 需要删除的全量数据同步配置主键集合
     * @return 结果
     */
    public int deleteDsFullSyncDbconfigByIDs(String IDs);

    /**
     * 删除全量数据同步配置信息
     * 
     * @param ID 全量数据同步配置主键
     * @return 结果
     */
    public int deleteDsFullSyncDbconfigByID(Long ID);
}
