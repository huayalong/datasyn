package com.ruoyi.datasync.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.datasync.domain.DsFullSyncDbconfig;
import com.ruoyi.datasync.domain.DsNodeDbConfig;
import com.ruoyi.datasync.service.IDsFullSyncDbconfigService;
import com.ruoyi.datasync.service.IDsNodeDbConfigService;
import com.ruoyi.datasync.support.SqlProperty;
import com.ruoyi.web.websocket.WebSocketHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * 全量数据同步配置Controller
 * 
 * @author qubo
 * @date 2022-10-13
 */
@Controller
@RequestMapping("/ds/fullsync")
public class DsFullSyncDbconfigController extends BaseController
{
    private String prefix = "ds/fullsync";


    @Autowired
    private WebSocketHandler webSocket;
    @Autowired
    private IDsNodeDbConfigService dbConfigService;
    @Autowired
    private IDsFullSyncDbconfigService dsFullSyncDbconfigService;

    @RequiresPermissions("ds:fullsync:view")
    @GetMapping()
    public String fullsync(ModelMap mmap)
    {
        mmap.put("dbList", dbConfigService.selectDsNodeDbConfigList(new DsNodeDbConfig()));
        return prefix + "/fullsync";
    }

    /**
     * 查询全量数据同步配置列表
     */
    @RequiresPermissions("ds:fullsync:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        startPage();
        List<DsFullSyncDbconfig> list = dsFullSyncDbconfigService.selectDsFullSyncDbconfigList(dsFullSyncDbconfig);
        return getDataTable(list);
    }

    /**
     * 导出全量数据同步配置列表
     */
    @RequiresPermissions("ds:fullsync:export")
    @Log(title = "全量数据同步配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        List<DsFullSyncDbconfig> list = dsFullSyncDbconfigService.selectDsFullSyncDbconfigList(dsFullSyncDbconfig);
        ExcelUtil<DsFullSyncDbconfig> util = new ExcelUtil<DsFullSyncDbconfig>(DsFullSyncDbconfig.class);
        return util.exportExcel(list, "全量数据同步配置数据");
    }

    /**
     * 新增全量数据同步配置
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("dbList", dbConfigService.selectDsNodeDbConfigList(new DsNodeDbConfig()));
        return prefix + "/add";
    }

    /**
     * 新增保存全量数据同步配置
     */
    @RequiresPermissions("ds:fullsync:add")
    @Log(title = "全量数据同步配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        return toAjax(dsFullSyncDbconfigService.insertDsFullSyncDbconfig(dsFullSyncDbconfig));
    }

    /**
     * 修改全量数据同步配置
     */
    @RequiresPermissions("ds:fullsync:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        DsFullSyncDbconfig dsFullSyncDbconfig = dsFullSyncDbconfigService.selectDsFullSyncDbconfigByID(id);
        mmap.put("dsFullSyncDbconfig", dsFullSyncDbconfig);
        mmap.put("dbList", dbConfigService.selectDsNodeDbConfigList(new DsNodeDbConfig()));
        return prefix + "/edit";
    }

    /**
     * 修改保存全量数据同步配置
     */
    @RequiresPermissions("ds:fullsync:edit")
    @Log(title = "全量数据同步配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DsFullSyncDbconfig dsFullSyncDbconfig)
    {
        return toAjax(dsFullSyncDbconfigService.updateDsFullSyncDbconfig(dsFullSyncDbconfig));
    }

    /**
     * 删除全量数据同步配置
     */
    @RequiresPermissions("ds:fullsync:remove")
    @Log(title = "全量数据同步配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(dsFullSyncDbconfigService.deleteDsFullSyncDbconfigByIDs(ids));
    }

    /**
     * 跳转数据同步实时监测页面
     * @param id
     * @return
     */
    @GetMapping("/toFullSyncView")
    public String toFullSyncView(ModelMap mmap, String id)
    {
        mmap.put("id", id);
        return prefix + "/fullsynclog";
    }

    /**
     * 全量同步数据
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/fullSyncData")
    public AjaxResult fullSyncData(long id)
    {
        DsFullSyncDbconfig fullSyncDbconfig = dsFullSyncDbconfigService.selectDsFullSyncDbconfigByID(id);
        DsNodeDbConfig sConf = dbConfigService.selectDsNodeDbConfigById(Long.valueOf(fullSyncDbconfig.getSourceDbId()));
        DsNodeDbConfig tConf = dbConfigService.selectDsNodeDbConfigById(Long.valueOf(fullSyncDbconfig.getTargetDbId()));

        ApplicationHome applicationHome = new ApplicationHome();
        String basePath = applicationHome.getDir().getAbsolutePath() + File.separator + "dbClient" + File.separator;
        String dbFileName = basePath + "backup" + File.separator + DateUtils.dateTimeNow() + "_" + sConf.getInstanceDb() + ".osr";
        String backupLog = basePath + "log" + File.separator + DateUtils.dateTimeNow() + "_bk.log";
        String resumeLog = basePath + "log" + File.separator + DateUtils.dateTimeNow() + "_rt.log";
        // 组织逻辑备份命令行
        String backupCmd = SqlProperty.BACKUP_CMD.replace("#USER#", sConf.getUserName()).replace("#PASSWORD#", sConf.getPassWord())
                .replace("#IP#", sConf.getInstanceIp()).replace("#PORT#", sConf.getInstancePort()).replace("#DB#", sConf.getInstanceDb())
                .replace("#SCHEMA#", fullSyncDbconfig.getSourceSchema()).replace("#BACK_DB_FILE#", dbFileName).replace("#BACK_LOG_FILE#", backupLog);
        //组织逻辑恢复命令行
        String resumeCmd = SqlProperty.RESUME_CMD.replace("#USER#", tConf.getUserName()).replace("#PASSWORD#", tConf.getPassWord())
                .replace("#IP#", tConf.getInstanceIp()).replace("#PORT#", tConf.getInstancePort()).replace("#DB#", tConf.getInstanceDb())
                .replace("#SCHEMA#", fullSyncDbconfig.getTargetSchema()).replace("#BACK_DB_FILE#", dbFileName).replace("#RESUME_LOG_FILE#", resumeLog);
        
        BufferedReader cd_br = null;
        
        try{
            //获取当前项目的路径
            String projectUrl = System.getProperty("user.dir");
            //从项目路径中截取盘符
            String disk = projectUrl.split(":")[0];
            Runtime run = Runtime.getRuntime();
            String cdCmd = "cmd.exe /c " + disk + ": && cd " + basePath + "bin" + File.separator + " &&";
            Process process = run.exec(cdCmd + backupCmd + " && " + resumeCmd);
            cd_br = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            String bachup_line;
            while ((bachup_line = cd_br.readLine()) != null) {
                webSocket.pushMsg(bachup_line);
                System.out.println("全量数据同步日志： ======================== " + bachup_line);
            }
            FileUtils.deleteFile(dbFileName);
            FileUtils.deleteFile(backupLog);
            FileUtils.deleteFile(resumeLog);
            fullSyncDbconfig.setLastSyncTime(new Date());
            dsFullSyncDbconfigService.updateDsFullSyncDbconfig(fullSyncDbconfig);
        }catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }finally {
            try {
                cd_br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.success();
    }
}
