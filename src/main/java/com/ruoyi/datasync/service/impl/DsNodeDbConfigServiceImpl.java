package com.ruoyi.datasync.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.datasync.mapper.DsNodeDbConfigMapper;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 节点中心数据源配置Service业务层处理
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Service
public class DsNodeDbConfigServiceImpl implements IDsNodeDbConfigService 
{
    @Autowired
    private DsNodeDbConfigMapper dsNodeDbConfigMapper;
    @Autowired
    private DsNodeCoreConfigServiceImpl configService;

    /**
     * 查询节点中心数据源配置
     * 
     * @param id 节点中心数据源配置主键
     * @return 节点中心数据源配置
     */
    @Override
    public DsNodeDbConfig selectDsNodeDbConfigById(Long id)
    {
        return dsNodeDbConfigMapper.selectDsNodeDbConfigById(id);
    }

    /**
     * 查询节点中心数据源配置列表
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 节点中心数据源配置
     */
    @Override
    public List<DsNodeDbConfig> selectDsNodeDbConfigList(DsNodeDbConfig dsNodeDbConfig)
    {
        return dsNodeDbConfigMapper.selectDsNodeDbConfigList(dsNodeDbConfig);
    }

    /**
     * 新增节点中心数据源配置
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 结果
     */
    @Override
    public int insertDsNodeDbConfig(DsNodeDbConfig dsNodeDbConfig)
    {
        int row = dsNodeDbConfigMapper.insertDsNodeDbConfig(dsNodeDbConfig);
        configService.initConfig();
        return row;
    }

    /**
     * 修改节点中心数据源配置
     * 
     * @param dsNodeDbConfig 节点中心数据源配置
     * @return 结果
     */
    @Override
    public int updateDsNodeDbConfig(DsNodeDbConfig dsNodeDbConfig)
    {
        int row = dsNodeDbConfigMapper.updateDsNodeDbConfig(dsNodeDbConfig);
        configService.initConfig();
        return row;
    }

    /**
     * 批量删除节点中心数据源配置
     * 
     * @param ids 需要删除的节点中心数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteDsNodeDbConfigByIds(String ids)
    {
        int row = dsNodeDbConfigMapper.deleteDsNodeDbConfigByIds(Convert.toStrArray(ids));
        configService.initConfig();
        return row;
    }

    /**
     * 删除节点中心数据源配置信息
     * 
     * @param id 节点中心数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteDsNodeDbConfigById(Long id)
    {
        int row = dsNodeDbConfigMapper.deleteDsNodeDbConfigById(id);
        configService.initConfig();
        return row;
    }

    @Override
    public List<DsNodeDbConfig> selectDsNodeDbConfigByIds(List<String> ids) {
        return dsNodeDbConfigMapper.selectDsNodeDbConfigByIds(ids);
    }

    @Override
    public List<DsNodeDbConfig> selectAll() {
        return dsNodeDbConfigMapper.selectAll();
    }
}
