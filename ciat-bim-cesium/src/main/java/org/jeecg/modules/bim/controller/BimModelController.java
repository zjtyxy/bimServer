package org.jeecg.modules.bim.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bim.entity.BimModel;
import org.jeecg.modules.bim.service.IBimModelService;

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
 * @Description: BIM模型
 * @Author: jeecg-boot
 * @Date:   2021-12-08
 * @Version: V1.0
 */
@Api(tags="BIM模型")
@RestController
@RequestMapping("/bim/bimModel")
@Slf4j
public class BimModelController extends JeecgController<BimModel, IBimModelService> {
	@Autowired
	private IBimModelService bimModelService;

	/**
	 * 分页列表查询
	 *
	 * @param bimModel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "BIM模型-分页列表查询")
	@ApiOperation(value="BIM模型-分页列表查询", notes="BIM模型-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BimModel bimModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BimModel> queryWrapper = QueryGenerator.initQueryWrapper(bimModel, req.getParameterMap());
		Page<BimModel> page = new Page<BimModel>(pageNo, pageSize);
		IPage<BimModel> pageList = bimModelService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param bimModel
	 * @return
	 */
	@AutoLog(value = "BIM模型-添加")
	@ApiOperation(value="BIM模型-添加", notes="BIM模型-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BimModel bimModel) {
		bimModelService.save(bimModel);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param bimModel
	 * @return
	 */
	@AutoLog(value = "BIM模型-编辑")
	@ApiOperation(value="BIM模型-编辑", notes="BIM模型-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BimModel bimModel) {
		bimModelService.updateById(bimModel);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "BIM模型-通过id删除")
	@ApiOperation(value="BIM模型-通过id删除", notes="BIM模型-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bimModelService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "BIM模型-批量删除")
	@ApiOperation(value="BIM模型-批量删除", notes="BIM模型-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bimModelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "BIM模型-通过id查询")
	@ApiOperation(value="BIM模型-通过id查询", notes="BIM模型-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BimModel bimModel = bimModelService.getById(id);
		if(bimModel==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bimModel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bimModel
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BimModel bimModel) {
        return super.exportXls(request, bimModel, BimModel.class, "BIM模型");
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
        return super.importExcel(request, response, BimModel.class);
    }

}
