package com.ruoyi.datasync.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.datasync.mapper.DsSameDataDbConfigMapper;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import com.ruoyi.datasync.service.IDsSameDataDbConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 同构数据同步配置Service业务层处理
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Service
public class DsSameDataDbConfigServiceImpl implements IDsSameDataDbConfigService
{
    @Autowired
    private DsSameDataDbConfigMapper dsSameDataDbconfigMapper;
    @Autowired
    private DsNodeCoreConfigServiceImpl configService;

    /**
     * 查询同构数据同步配置
     * 
     * @param id 同构数据同步配置主键
     * @return 同构数据同步配置
     */
    @Override
    public DsSameDataDbConfig selectDsSameDataDbconfigById(Long id)
    {
        return dsSameDataDbconfigMapper.selectDsSameDataDbconfigById(id);
    }

    /**
     * 查询同构数据同步配置列表
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 同构数据同步配置
     */
    @Override
    public List<DsSameDataDbConfig> selectDsSameDataDbconfigList(DsSameDataDbConfig dsSameDataDbconfig)
    {
        return dsSameDataDbconfigMapper.selectDsSameDataDbconfigList(dsSameDataDbconfig);
    }

    /**
     * 新增同构数据同步配置
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 结果
     */
    @Override
    public int insertDsSameDataDbconfig(DsSameDataDbConfig dsSameDataDbconfig)
    {
        int row = dsSameDataDbconfigMapper.insertDsSameDataDbconfig(dsSameDataDbconfig);
        configService.initConfig();
        return row;
    }

    /**
     * 修改同构数据同步配置
     * 
     * @param dsSameDataDbconfig 同构数据同步配置
     * @return 结果
     */
    @Override
    public int updateDsSameDataDbconfig(DsSameDataDbConfig dsSameDataDbconfig)
    {
        int row = dsSameDataDbconfigMapper.updateDsSameDataDbconfig(dsSameDataDbconfig);
        configService.initConfig();
        return row;
    }

    /**
     * 批量删除同构数据同步配置
     * 
     * @param ids 需要删除的同构数据同步配置主键
     * @return 结果
     */
    @Override
    public int deleteDsSameDataDbconfigByIds(String ids)
    {
        int row = dsSameDataDbconfigMapper.deleteDsSameDataDbconfigByIds(Convert.toStrArray(ids));
        configService.initConfig();
        return row;
    }

    /**
     * 删除同构数据同步配置信息
     * 
     * @param id 同构数据同步配置主键
     * @return 结果
     */
    @Override
    public int deleteDsSameDataDbconfigById(Long id)
    {
        int row = dsSameDataDbconfigMapper.deleteDsSameDataDbconfigById(id);
        configService.initConfig();
        return row;
    }
}
