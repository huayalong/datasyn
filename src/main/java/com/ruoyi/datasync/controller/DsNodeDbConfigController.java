package com.ruoyi.datasync.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.datasync.domain.DsNodeCoreConfig;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IDsNodeCoreConfigService;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;
import com.ruoyi.datasync.support.DBUtil;
import com.ruoyi.datasync.support.SchemaTree;
import com.ruoyi.datasync.support.SqlProperty;
import com.ruoyi.datasync.support.TableColumn;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 节点中心数据源配置Controller
 * 
 * @author qubo
 * @date 2022-10-11
 */
@Controller
@RequestMapping("/ds/nodeDb")
public class DsNodeDbConfigController extends BaseController
{
    private String prefix = "ds/nodeDb";

    @Autowired
    private IDsNodeCoreConfigService nodeCoreConfigService;
    @Autowired
    private IDsNodeDbConfigService dsNodeDbConfigService;

    @RequiresPermissions("ds:nodeDb:view")
    @GetMapping()
    public String nodeDb()
    {
        return prefix + "/nodeDb";
    }

    /**
     * 查询节点中心数据源配置列表
     */
    @RequiresPermissions("ds:nodeDb:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DsNodeDbConfig dsNodeDbConfig)
    {
        startPage();
        List<DsNodeDbConfig> list = dsNodeDbConfigService.selectDsNodeDbConfigList(dsNodeDbConfig);
        return getDataTable(list);
    }

    /**
     * 导出节点中心数据源配置列表
     */
    @RequiresPermissions("ds:nodeDb:export")
    @Log(title = "节点中心数据源配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DsNodeDbConfig dsNodeDbConfig)
    {
        List<DsNodeDbConfig> list = dsNodeDbConfigService.selectDsNodeDbConfigList(dsNodeDbConfig);
        ExcelUtil<DsNodeDbConfig> util = new ExcelUtil<DsNodeDbConfig>(DsNodeDbConfig.class);
        return util.exportExcel(list, "节点中心数据源配置数据");
    }

    /**
     * 新增节点中心数据源配置
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("nodeList", nodeCoreConfigService.selectDsNodeCoreConfigList(new DsNodeCoreConfig()));
        return prefix + "/add";
    }

    /**
     * 新增保存节点中心数据源配置
     */
    @RequiresPermissions("ds:nodeDb:add")
    @Log(title = "节点中心数据源配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DsNodeDbConfig dsNodeDbConfig)
    {
        return toAjax(dsNodeDbConfigService.insertDsNodeDbConfig(dsNodeDbConfig));
    }

    /**
     * 修改节点中心数据源配置
     */
    @RequiresPermissions("ds:nodeDb:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        mmap.put("dsNodeDbConfig", dsNodeDbConfigService.selectDsNodeDbConfigById(id));
        mmap.put("nodeList", nodeCoreConfigService.selectDsNodeCoreConfigList(new DsNodeCoreConfig()));
        return prefix + "/edit";
    }

    /**
     * 修改保存节点中心数据源配置
     */
    @RequiresPermissions("ds:nodeDb:edit")
    @Log(title = "节点中心数据源配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DsNodeDbConfig dsNodeDbConfig)
    {
        return toAjax(dsNodeDbConfigService.updateDsNodeDbConfig(dsNodeDbConfig));
    }

    /**
     * 删除节点中心数据源配置
     */
    @RequiresPermissions("ds:nodeDb:remove")
    @Log(title = "节点中心数据源配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dsNodeDbConfigService.deleteDsNodeDbConfigByIds(ids));
    }
    
    /**
     * 测试数据库连接
     */
    @ResponseBody
    @PostMapping("/testConn")
    public AjaxResult testConn(long id)
    {
        try{
            DsNodeDbConfig conf = dsNodeDbConfigService.selectDsNodeDbConfigById(id);
            DriverManagerDataSource dataSource = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            Connection connection = dataSource.getConnection();
            if(!connection.isClosed()){
                connection.close();
            }
            return AjaxResult.success();
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 预览数据界面
     */
    @GetMapping("/dbpreview")
    public String dbPreview(ModelMap mmap, String id)
    {
        mmap.put("dbConfId", id);
        return prefix + "/dbpreview";
    }

    /**
     * 模式、表树形数据
     * @return
     */
    @ResponseBody
    @GetMapping("/schemaTableTree")
    public List<SchemaTree> schemaTableTree(@RequestParam("id") long id)
    {
        try{
            List<SchemaTree> schemaTrees = new ArrayList<>();
            DsNodeDbConfig conf = dsNodeDbConfigService.selectDsNodeDbConfigById(id);
            DriverManagerDataSource dataSource = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            List<String> schemas = DBUtil.getSchemaListBySource(dataSource);
            schemas.stream().forEach(schema -> {
                schemaTrees.add(new SchemaTree(schema, schema, schema, schema));
                List<String> tables = DBUtil.getTableListBySchema(dataSource, schema);
                tables.stream().forEach(table ->{
                    schemaTrees.add(new SchemaTree(table, schema, table, table));
                });
            });
            return schemaTrees;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }

    }

    /**
     * 根据模式名、表名获取所有字段
     * @return
     */
    @ResponseBody
    @PostMapping("/getColumnsBySchemaAndTable")
    public AjaxResult getColumnsBySchemaAndTable(long id, String dbSchema, String tableName)
    {
        try{
            DsNodeDbConfig conf = dsNodeDbConfigService.selectDsNodeDbConfigById(id);
            DriverManagerDataSource dataSource = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = SqlProperty.ALL_COLUMN_SQL.replace("#SCHEMA#", dbSchema).replace("#TABLE#", tableName);
            List<String> columns = jdbcTemplate.queryForList(sql, String.class);
            List<TableColumn> columnList = new ArrayList<>();
            columns.stream().forEach(column -> columnList.add(new TableColumn(column, column)));
            return AjaxResult.success(columnList);
        }catch (Exception e){
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据模式名、表名获取所有字段
     * @return
     */
    @ResponseBody
    @PostMapping("/selectDataBySchemaAndTable")
    public TableDataInfo selectDataBySchemaAndTable(long id, String schema, String tableName)
    {
        try{
            DsNodeDbConfig conf = dsNodeDbConfigService.selectDsNodeDbConfigById(id);
            DriverManagerDataSource dataSource = DBUtil.initDatasource(conf.getInstanceIp(), conf.getInstancePort(), conf.getInstanceDb(), conf.getUserName(), conf.getPassWord());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = SqlProperty.SELECT_DATA_SQL.replace("#SCHEMA#", schema).replace("#TABLE#", tableName);
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
            return new TableDataInfo(maps, maps.size());
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
