package com.ruoyi.datasync.service;

import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.support.NodeGraph;

import java.util.List;

/**
 * 节点中心配置Service接口
 * 
 * @author qubo
 * @date 2022-10-11
 */
public interface IDsNodeCoreConfigService 
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
     * 批量删除节点中心配置
     * 
     * @param ids 需要删除的节点中心配置主键集合
     * @return 结果
     */
    public int deleteDsNodeCoreConfigByIds(String ids);

    /**
     * 删除节点中心配置信息
     * 
     * @param id 节点中心配置主键
     * @return 结果
     */
    public int deleteDsNodeCoreConfigById(Long id);

    /**
     * 获取所有数据
     * @return
     */
    List<DsNodeCoreConfig> selectAll();

    /**
     * 获取图谱数据
     * @return
     */
    NodeGraph  genNodeCoreGraphData();
}
