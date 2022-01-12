package org.jeecg.modules.base.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.base.entity.BimGtlfModel;
import org.jeecg.modules.base.service.IBimGtlfModelService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.bim.entity.BimModel;
import org.jeecg.modules.system.service.ISysDictService;
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
 * @Description: 小模型库
 * @Author: jeecg-boot
 * @Date:   2021-12-10
 * @Version: V1.0
 */
@Api(tags="小模型库")
@RestController
@RequestMapping("/base/bimGtlfModel")
@Slf4j
public class BimGtlfModelController extends JeecgController<BimGtlfModel, IBimGtlfModelService> {
	@Autowired
	private IBimGtlfModelService bimGtlfModelService;


	 @Autowired
	 private ISysDictService sysDictService;


	/**
	 * 分页列表查询
	 *
	 * @param bimGtlfModel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "小模型库-分页列表查询")
	@ApiOperation(value="小模型库-分页列表查询", notes="小模型库-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BimGtlfModel bimGtlfModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BimGtlfModel> queryWrapper = QueryGenerator.initQueryWrapper(bimGtlfModel, req.getParameterMap());
		Page<BimGtlfModel> page = new Page<BimGtlfModel>(pageNo, pageSize);
		IPage<BimGtlfModel> pageList = bimGtlfModelService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	 @AutoLog(value = "BIM模型-分页列表查询")
	 @ApiOperation(value="BIM模型-分页列表查询", notes="BIM模型-分页列表查询")
	 @GetMapping(value = "/listMap")
	 public Result<?> queryListMap(BimGtlfModel bimModel, HttpServletRequest req) {
		 QueryWrapper<BimGtlfModel> queryWrapper = QueryGenerator.initQueryWrapper(bimModel, req.getParameterMap());
		 List<BimGtlfModel> pageList = bimGtlfModelService.list(queryWrapper);
		 Map<String,List<BimGtlfModel>> rst = new HashMap<>();
		 List<DictModel> dicts = sysDictService.getDictItems("gltf_type");
		 for(BimGtlfModel bm : pageList)
		 {
		 	bm.setClassType(this.translDictText(dicts,bm.getClassType()));
			List<BimGtlfModel>  bgl=  MapUtils.getObject(rst,bm.getClassType(),new ArrayList<BimGtlfModel>());
			if(bgl.isEmpty())
			{
				rst.put(bm.getClassType(),bgl);
			}
			 bgl.add(bm);
		 }

		 return Result.OK(rst);
	 }
	/**
	 *   添加
	 *
	 * @param bimGtlfModel
	 * @return
	 */
	@AutoLog(value = "小模型库-添加")
	@ApiOperation(value="小模型库-添加", notes="小模型库-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BimGtlfModel bimGtlfModel) {
		bimGtlfModelService.save(bimGtlfModel);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param bimGtlfModel
	 * @return
	 */
	@AutoLog(value = "小模型库-编辑")
	@ApiOperation(value="小模型库-编辑", notes="小模型库-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BimGtlfModel bimGtlfModel) {
		bimGtlfModelService.updateById(bimGtlfModel);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "小模型库-通过id删除")
	@ApiOperation(value="小模型库-通过id删除", notes="小模型库-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bimGtlfModelService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "小模型库-批量删除")
	@ApiOperation(value="小模型库-批量删除", notes="小模型库-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bimGtlfModelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "小模型库-通过id查询")
	@ApiOperation(value="小模型库-通过id查询", notes="小模型库-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BimGtlfModel bimGtlfModel = bimGtlfModelService.getById(id);
		if(bimGtlfModel==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bimGtlfModel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bimGtlfModel
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BimGtlfModel bimGtlfModel) {
        return super.exportXls(request, bimGtlfModel, BimGtlfModel.class, "小模型库");
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
        return super.importExcel(request, response, BimGtlfModel.class);
    }
	 private String translDictText(List<DictModel> dictModels, String values) {
		 List<String> result = new ArrayList<>();

		 // 允许多个逗号分隔，允许传数组对象
		 String[] splitVal = values.split(",");
		 for (String val : splitVal) {
			 String dictText = val;
			 for (DictModel dict : dictModels) {
				 if (val.equals(dict.getValue())) {
					 dictText = dict.getText();
					 break;
				 }
			 }
			 result.add(dictText);
		 }
		 return String.join(",", result);
	 }
}
