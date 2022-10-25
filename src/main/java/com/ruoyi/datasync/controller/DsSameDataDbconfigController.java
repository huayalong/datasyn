package com.ruoyi.datasync.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.domain.DsSameDataDbConfig;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;
import com.ruoyi.datasync.service.IDsSameDataDbConfigService;
import com.ruoyi.datasync.support.DBUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 同构数据同步配置Controller
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Controller
@RequestMapping("/ds/sameDbConf")
public class DsSameDataDbconfigController extends BaseController
{
    private String prefix = "ds/sameDbConf";

    @Autowired
    private IDsNodeDbConfigService dbConfigService;
    @Autowired
    private IDsSameDataDbConfigService dsSameDataDbconfigService;

    @RequiresPermissions("ds:sameDbConf:view")
    @GetMapping()
    public String sameDbConf()
    {
        return prefix + "/sameDbConf";
    }

    /**
     * 查询同构数据同步配置列表
     */
    @RequiresPermissions("ds:sameDbConf:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DsSameDataDbConfig dsSameDataDbconfig)
    {
        startPage();
        List<DsSameDataDbConfig> list = dsSameDataDbconfigService.selectDsSameDataDbconfigList(dsSameDataDbconfig);
        return getDataTable(list);
    }

    /**
     * 导出同构数据同步配置列表
     */
    @RequiresPermissions("ds:sameDbConf:export")
    @Log(title = "同构数据同步配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DsSameDataDbConfig dsSameDataDbconfig)
    {
        List<DsSameDataDbConfig> list = dsSameDataDbconfigService.selectDsSameDataDbconfigList(dsSameDataDbconfig);
        ExcelUtil<DsSameDataDbConfig> util = new ExcelUtil<DsSameDataDbConfig>(DsSameDataDbConfig.class);
        return util.exportExcel(list, "同构数据同步配置数据");
    }

    /**
     * 新增同构数据同步配置
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("dbList", dbConfigService.selectDsNodeDbConfigList(new DsNodeDbConfig()));
        return prefix + "/add";
    }

    /**
     * 新增保存同构数据同步配置
     */
    @RequiresPermissions("ds:sameDbConf:add")
    @Log(title = "同构数据同步配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DsSameDataDbConfig dsSameDataDbconfig)
    {
        return toAjax(dsSameDataDbconfigService.insertDsSameDataDbconfig(dsSameDataDbconfig));
    }

    /**
     * 修改同构数据同步配置
     */
    @RequiresPermissions("ds:sameDbConf:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DsSameDataDbConfig sameConfig = dsSameDataDbconfigService.selectDsSameDataDbconfigById(id);
        mmap.put("dsSameDataDbconfig", sameConfig);

        // 回显下拉框选择所有数据源
        mmap.put("dbList", dbConfigService.selectDsNodeDbConfigList(new DsNodeDbConfig()));
        // 回显下拉框选择所有模式
        DsNodeDbConfig sConf = dbConfigService.selectDsNodeDbConfigById(Long.valueOf(sameConfig.getSourceDbId()));
        DsNodeDbConfig tConf = dbConfigService.selectDsNodeDbConfigById(Long.valueOf(sameConfig.getTargetDbId()));
        DriverManagerDataSource sSource = DBUtil.initDatasource(sConf.getInstanceIp(), sConf.getInstancePort(), sConf.getInstanceDb(), sConf.getUserName(), sConf.getPassWord());
        DriverManagerDataSource tSource = DBUtil.initDatasource(tConf.getInstanceIp(), tConf.getInstancePort(), tConf.getInstanceDb(), tConf.getUserName(), tConf.getPassWord());
        mmap.put("sourceSchemas", DBUtil.getSchemaListBySource(sSource));
        mmap.put("targetSchemas", DBUtil.getSchemaListBySource(tSource));
        // 回显下拉框选择所有数据库表
        mmap.put("sourceTables", DBUtil.getTableListBySchema(sSource, sameConfig.getSourceSchema()));
        mmap.put("targetTables", DBUtil.getTableListBySchema(tSource, sameConfig.getTargetSchema()));

        return prefix + "/edit";
    }

    /**
     * 修改保存同构数据同步配置
     */
    @RequiresPermissions("ds:sameDbConf:edit")
    @Log(title = "同构数据同步配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DsSameDataDbConfig dsSameDataDbconfig)
    {
        return toAjax(dsSameDataDbconfigService.updateDsSameDataDbconfig(dsSameDataDbconfig));
    }

    /**
     * 删除同构数据同步配置
     */
    @RequiresPermissions("ds:sameDbConf:remove")
    @Log(title = "同构数据同步配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dsSameDataDbconfigService.deleteDsSameDataDbconfigByIds(ids));
    }

    /**
     * 根据数据源配置获取所有模式
     * @param sourceId
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllSchema")
    public AjaxResult getAllSchema(@RequestParam("sourceId") Long sourceId)
    {
        try{
            DsNodeDbConfig conf = dbConfigService.selectDsNodeDbConfigById(sourceId);
            DriverManagerDataSource sSource = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            return AjaxResult.success(DBUtil.getSchemaListBySource(sSource));
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据数据源配置和模式名关联查询所有数据库表
     * @param sourceId
     * @param sourceId
     * @return
     */
    @ResponseBody
    @GetMapping("/getAllTablesBySchema")
    public AjaxResult getAllTablesBySchema(@RequestParam("sourceId") Long sourceId, @RequestParam("schema") String schema)
    {
        try{
            DsNodeDbConfig conf = dbConfigService.selectDsNodeDbConfigById(sourceId);
            DriverManagerDataSource source = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            return AjaxResult.success(DBUtil.getTableListBySchema(source, schema));
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }
}
