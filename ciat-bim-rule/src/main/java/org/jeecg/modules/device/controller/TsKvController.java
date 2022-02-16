package org.jeecg.modules.device.controller;

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
import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.service.ITsKvService;

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
 * @Description: 设备遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
@Api(tags="设备遥测数据")
@RestController
@RequestMapping("/device/tsKv")
@Slf4j
public class TsKvController extends JeecgController<TsKv, ITsKvService> {
	@Autowired
	private ITsKvService tsKvService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tsKv
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-分页列表查询")
	@ApiOperation(value="设备遥测数据-分页列表查询", notes="设备遥测数据-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TsKv tsKv,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TsKv> queryWrapper = QueryGenerator.initQueryWrapper(tsKv, req.getParameterMap());
		Page<TsKv> page = new Page<TsKv>(pageNo, pageSize);
		IPage<TsKv> pageList = tsKvService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tsKv
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-添加")
	@ApiOperation(value="设备遥测数据-添加", notes="设备遥测数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TsKv tsKv) {
		tsKvService.save(tsKv);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tsKv
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-编辑")
	@ApiOperation(value="设备遥测数据-编辑", notes="设备遥测数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TsKv tsKv) {
		tsKvService.updateById(tsKv);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-通过id删除")
	@ApiOperation(value="设备遥测数据-通过id删除", notes="设备遥测数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tsKvService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-批量删除")
	@ApiOperation(value="设备遥测数据-批量删除", notes="设备遥测数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tsKvService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备遥测数据-通过id查询")
	@ApiOperation(value="设备遥测数据-通过id查询", notes="设备遥测数据-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TsKv tsKv = tsKvService.getById(id);
		if(tsKv==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(tsKv);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tsKv
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TsKv tsKv) {
        return super.exportXls(request, tsKv, TsKv.class, "设备遥测数据");
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
        return super.importExcel(request, response, TsKv.class);
    }

}
