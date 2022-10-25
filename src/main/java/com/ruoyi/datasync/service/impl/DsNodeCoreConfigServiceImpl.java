package com.ruoyi.datasync.service.impl;

import com.ruoyi.common.constant.CacheKeyConstants;
import com.ruoyi.common.constant.DataSynchronizationConstant;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.datasync.domain.DsConfigResult;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import com.ruoyi.datasync.mapper.DsNodeCoreConfigMapper;
import com.ruoyi.datasync.mapper.DsSameDataDbConfigMapper;
import com.ruoyi.datasync.service.IDsNodeCoreConfigService;
import com.ruoyi.datasync.support.NodeGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 节点中心配置Service业务层处理
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Service
public class DsNodeCoreConfigServiceImpl implements IDsNodeCoreConfigService 
{
    @Value("${coreNode.ip}")
    private String nodeIp;
    @Autowired
    private DsNodeCoreConfigMapper dsNodeCoreConfigMapper;
    @Autowired
    private DsSameDataDbConfigMapper sameDataDbConfigMapper;


    /**
     * 查询节点中心配置
     * 
     * @param id 节点中心配置主键
     * @return 节点中心配置
     */
    @Override
    public DsNodeCoreConfig selectDsNodeCoreConfigById(Long id)
    {
        return dsNodeCoreConfigMapper.selectDsNodeCoreConfigById(id);
    }

    /**
     * 查询节点中心配置列表
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 节点中心配置
     */
    @Override
    public List<DsNodeCoreConfig> selectDsNodeCoreConfigList(DsNodeCoreConfig dsNodeCoreConfig)
    {
        return dsNodeCoreConfigMapper.selectDsNodeCoreConfigList(dsNodeCoreConfig);
    }

    /**
     * 新增节点中心配置
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 结果
     */
    @Override
    public int insertDsNodeCoreConfig(DsNodeCoreConfig dsNodeCoreConfig)
    {
        if(DataSynchronizationConstant.MASTER_NODE.equals(dsNodeCoreConfig.getIsMaster())){
            dsNodeCoreConfigMapper.clearOtherMaster(dsNodeCoreConfig);
        }
        int row = dsNodeCoreConfigMapper.insertDsNodeCoreConfig(dsNodeCoreConfig);

        initConfig();
        return row;
    }

    /**
     * 修改节点中心配置
     * 
     * @param dsNodeCoreConfig 节点中心配置
     * @return 结果
     */
    @Override
    public int updateDsNodeCoreConfig(DsNodeCoreConfig dsNodeCoreConfig)
    {
        if(DataSynchronizationConstant.MASTER_NODE.equals(dsNodeCoreConfig.getIsMaster())){
            dsNodeCoreConfigMapper.clearOtherMaster(dsNodeCoreConfig);
        }
        int row = dsNodeCoreConfigMapper.updateDsNodeCoreConfig(dsNodeCoreConfig);
        initConfig();
        return row;
    }

    /**
     * 批量删除节点中心配置
     * 
     * @param ids 需要删除的节点中心配置主键
     * @return 结果
     */
    @Override
    public int deleteDsNodeCoreConfigByIds(String ids)
    {
        int row = dsNodeCoreConfigMapper.deleteDsNodeCoreConfigByIds(Convert.toStrArray(ids));
        initConfig();
        return row;
    }

    /**
     * 删除节点中心配置信息
     * 
     * @param id 节点中心配置主键
     * @return 结果
     */
    @Override
    public int deleteDsNodeCoreConfigById(Long id)
    {
        int row = dsNodeCoreConfigMapper.deleteDsNodeCoreConfigById(id);
        initConfig();
        return row;
    }

    @Override
    public List<DsNodeCoreConfig> selectAll() {
        return dsNodeCoreConfigMapper.selectAll();
    }

    @Override
    public NodeGraph genNodeCoreGraphData() {
        List<NodeGraph.Node> nodes = new ArrayList<>();
        List<NodeGraph.Link> links = new ArrayList<>();
        NodeGraph.Topo topo = new NodeGraph.Topo(nodes, links);
        List<DsNodeCoreConfig> confs = dsNodeCoreConfigMapper.selectDsNodeCoreConfigList(new DsNodeCoreConfig());
        List<DsSameDataDbConfig> dbConfigs = sameDataDbConfigMapper.selectDsSameDataDbconfigList(new DsSameDataDbConfig());
        confs.stream().forEach(conf -> {
            nodes.add(new NodeGraph.Node(conf.getId().toString(), conf.getNodeIp(), conf.getCoreName()));
        });
        dbConfigs.stream().forEach(conf -> {
            links.add(new NodeGraph.Link(conf.getSourceNodeId(), conf.getTargetNodeId()));
        });


        return new NodeGraph(topo);
    }

    /**
     * 项目启动将配置信息存入缓存
     */
    @PostConstruct
    public void initConfig() {
        CacheUtils.remove(CacheKeyConstants.DATA_SYNC_KEY);
        List<DsConfigResult> coreConfigs = dsNodeCoreConfigMapper.selectCoreConfig();
        CacheUtils.put(CacheKeyConstants.DATA_SYNC_KEY, coreConfigs);
    }
}
