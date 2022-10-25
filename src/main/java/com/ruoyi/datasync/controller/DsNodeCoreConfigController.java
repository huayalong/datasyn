package com.ruoyi.datasync.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.service.IBinlogEndPosService;
import com.ruoyi.datasync.service.IDsNodeCoreConfigService;
import com.ruoyi.datasync.support.NodeGraph;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节点中心配置Controller
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Controller
@RequestMapping("/ds/nodeCore")
public class DsNodeCoreConfigController extends BaseController
{
    private String prefix = "ds/nodeCore";

    @Autowired
    private IDsNodeCoreConfigService dsNodeCoreConfigService;


    @Autowired
    private IBinlogEndPosService binlogEndPosService;

    @RequiresPermissions("ds:nodeCore:view")
    @GetMapping()
    public String nodeCore()
    {
        return prefix + "/nodeCore";
    }

    /**
     * 查询节点中心配置列表
     */
    @RequiresPermissions("ds:nodeCore:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DsNodeCoreConfig dsNodeCoreConfig)
    {
        startPage();
        List<DsNodeCoreConfig> list = dsNodeCoreConfigService.selectDsNodeCoreConfigList(dsNodeCoreConfig);
        return getDataTable(list);
    }

    /**
     * 导出节点中心配置列表
     */
    @RequiresPermissions("ds:nodeCore:export")
    @Log(title = "节点中心配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DsNodeCoreConfig dsNodeCoreConfig)
    {
        List<DsNodeCoreConfig> list = dsNodeCoreConfigService.selectDsNodeCoreConfigList(dsNodeCoreConfig);
        ExcelUtil<DsNodeCoreConfig> util = new ExcelUtil<DsNodeCoreConfig>(DsNodeCoreConfig.class);
        return util.exportExcel(list, "节点中心配置数据");
    }

    /**
     * 新增节点中心配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存节点中心配置
     */
    @RequiresPermissions("ds:nodeCore:add")
    @Log(title = "节点中心配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DsNodeCoreConfig dsNodeCoreConfig)
    {
        return toAjax(dsNodeCoreConfigService.insertDsNodeCoreConfig(dsNodeCoreConfig));
    }

    /**
     * 修改节点中心配置
     */
    @RequiresPermissions("ds:nodeCore:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DsNodeCoreConfig dsNodeCoreConfig = dsNodeCoreConfigService.selectDsNodeCoreConfigById(id);
        mmap.put("dsNodeCoreConfig", dsNodeCoreConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存节点中心配置
     */
    @RequiresPermissions("ds:nodeCore:edit")
    @Log(title = "节点中心配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DsNodeCoreConfig dsNodeCoreConfig)
    {
        return toAjax(dsNodeCoreConfigService.updateDsNodeCoreConfig(dsNodeCoreConfig));
    }

    /**
     * 删除节点中心配置
     */
    @RequiresPermissions("ds:nodeCore:remove")
    @Log(title = "节点中心配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dsNodeCoreConfigService.deleteDsNodeCoreConfigByIds(ids));
    }


    @ApiOperation("初始化Binlog位移")
    @ResponseBody
    @GetMapping("/syncBinlog")
    public R<String> syncBinlog() {
        binlogEndPosService.initEndPosByLog();
        return R.ok();
    }

    @ResponseBody
    @GetMapping("/nodeCoreGraph")
    public NodeGraph nodeCoreGraph()
    {
        NodeGraph graph = dsNodeCoreConfigService.genNodeCoreGraphData();
        return graph;
    }
}
