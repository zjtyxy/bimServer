package org.jeecg.modules.alarm.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.service.IAlarmService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 报警信息
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
@Api(tags="报警信息")
@RestController
@RequestMapping("/alarm/alarm")
@Slf4j
public class AlarmController extends JeecgController<Alarm, IAlarmService> {
	@Autowired
	private IAlarmService alarmService;
	
	/**
	 * 分页列表查询
	 *
	 * @param alarm
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "报警信息-分页列表查询")
	@ApiOperation(value="报警信息-分页列表查询", notes="报警信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Alarm alarm,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Alarm> queryWrapper = QueryGenerator.initQueryWrapper(alarm, req.getParameterMap());
		Page<Alarm> page = new Page<Alarm>(pageNo, pageSize);
		IPage<Alarm> pageList = alarmService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param alarm
	 * @return
	 */
	@AutoLog(value = "报警信息-添加")
	@ApiOperation(value="报警信息-添加", notes="报警信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Alarm alarm) {
		alarmService.save(alarm);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param alarm
	 * @return
	 */
	@AutoLog(value = "报警信息-编辑")
	@ApiOperation(value="报警信息-编辑", notes="报警信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Alarm alarm) {
		alarmService.updateById(alarm);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报警信息-通过id删除")
	@ApiOperation(value="报警信息-通过id删除", notes="报警信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		alarmService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报警信息-批量删除")
	@ApiOperation(value="报警信息-批量删除", notes="报警信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.alarmService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报警信息-通过id查询")
	@ApiOperation(value="报警信息-通过id查询", notes="报警信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Alarm alarm = alarmService.getById(id);
		if(alarm==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(alarm);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param alarm
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Alarm alarm) {
        return super.exportXls(request, alarm, Alarm.class, "报警信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Alarm.class);
    }

}
