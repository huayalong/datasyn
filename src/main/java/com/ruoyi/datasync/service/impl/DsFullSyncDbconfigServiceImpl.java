package com.ruoyi.datasync.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.datasync.mapper.DsFullSyncDbconfigMapper;
import com.ruoyi.datasync.domain.DsFullSyncDbconfig;
import com.ruoyi.datasync.service.IDsFullSyncDbconfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 全量数据同步配置Service业务层处理
 * 
 * @author qubo
 * @date 2022-10-13
 */
@Service
public class DsFullSyncDbconfigServiceImpl implements IDsFullSyncDbconfigService 
{
    @Autowired
    private DsFullSyncDbconfigMapper dsFullSyncDbconfigMapper;

    /**
     * 查询全量数据同步配置
     * 
     * @param ID 全量数据同步配置主键
     * @return 全量数据同步配置
     */
    @Override
    public DsFullSyncDbconfig selectDsFullSyncDbconfigByID(Long ID)
    {
        return dsFullSyncDbconfigMapper.selectDsFullSyncDbconfigByID(ID);
    }

    /**
     * 查询全量数据同步配置列表
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 全量数据同步配置
     */
    @Override
    public List<DsFullSyncDbconfig> selectDsFullSyncDbconfigList(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        return dsFullSyncDbconfigMapper.selectDsFullSyncDbconfigList(dsFullSyncDbconfig);
    }

    /**
     * 新增全量数据同步配置
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 结果
     */
    @Override
    public int insertDsFullSyncDbconfig(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        dsFullSyncDbconfig.setCreateTime(DateUtils.getNowDate());
        return dsFullSyncDbconfigMapper.insertDsFullSyncDbconfig(dsFullSyncDbconfig);
    }

    /**
     * 修改全量数据同步配置
     * 
     * @param dsFullSyncDbconfig 全量数据同步配置
     * @return 结果
     */
    @Override
    public int updateDsFullSyncDbconfig(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        return dsFullSyncDbconfigMapper.updateDsFullSyncDbconfig(dsFullSyncDbconfig);
    }

    /**
     * 批量删除全量数据同步配置
     * 
     * @param IDs 需要删除的全量数据同步配置主键
     * @return 结果
     */
    @Override
    public int deleteDsFullSyncDbconfigByIDs(String IDs)
    {
        return dsFullSyncDbconfigMapper.deleteDsFullSyncDbconfigByIDs(Convert.toStrArray(IDs));
    }

    /**
     * 删除全量数据同步配置信息
     * 
     * @param ID 全量数据同步配置主键
     * @return 结果
     */
    @Override
    public int deleteDsFullSyncDbconfigByID(Long ID)
    {
        return dsFullSyncDbconfigMapper.deleteDsFullSyncDbconfigByID(ID);
    }
}
