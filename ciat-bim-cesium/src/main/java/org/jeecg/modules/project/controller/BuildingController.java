package org.jeecg.modules.project.controller;

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
import org.jeecg.modules.project.entity.Building;
import org.jeecg.modules.project.entity.HeatingUnit;
import org.jeecg.modules.project.service.IBuildingService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.project.service.IHeatingUnitService;
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
 * @Description: 楼栋信息
 * @Author: jeecg-boot
 * @Date:   2022-03-17
 * @Version: V1.0
 */
@Api(tags="楼栋信息")
@RestController
@RequestMapping("/project/building")
@Slf4j
public class BuildingController extends JeecgController<Building, IBuildingService> {
	@Autowired
	private IBuildingService buildingService;
	 @Autowired
	 private IHeatingUnitService heatingUnitService;
	/**
	 * 分页列表查询
	 *
	 * @param building
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "楼栋信息-分页列表查询")
	@ApiOperation(value="楼栋信息-分页列表查询", notes="楼栋信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Building building,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Building> queryWrapper = QueryGenerator.initQueryWrapper(building, req.getParameterMap());
		Page<Building> page = new Page<Building>(pageNo, pageSize);
		IPage<Building> pageList = buildingService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	 @AutoLog(value = "楼栋信息-分页列表查询")
	 @ApiOperation(value="楼栋信息-分页列表查询", notes="楼栋信息-分页列表查询")
	 @GetMapping(value = "/roomlist")
	 public Result<?> queryRoomList(Building building,
									HttpServletRequest req) {
		 QueryWrapper<HeatingUnit> queryWrapper = new QueryWrapper<>();
		 queryWrapper.eq("room_buiding",building.getId());
		 List<HeatingUnit> pageList = heatingUnitService.list(queryWrapper);

		 return Result.OK(pageList);
	 }
	/**
	 *   添加
	 *
	 * @param building
	 * @return
	 */
	@AutoLog(value = "楼栋信息-添加")
	@ApiOperation(value="楼栋信息-添加", notes="楼栋信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Building building) {
		buildingService.save(building);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param building
	 * @return
	 */
	@AutoLog(value = "楼栋信息-编辑")
	@ApiOperation(value="楼栋信息-编辑", notes="楼栋信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Building building) {
		buildingService.updateById(building);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "楼栋信息-通过id删除")
	@ApiOperation(value="楼栋信息-通过id删除", notes="楼栋信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		buildingService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "楼栋信息-批量删除")
	@ApiOperation(value="楼栋信息-批量删除", notes="楼栋信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.buildingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "楼栋信息-通过id查询")
	@ApiOperation(value="楼栋信息-通过id查询", notes="楼栋信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Building building = buildingService.getById(id);
		if(building==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(building);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param building
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Building building) {
        return super.exportXls(request, building, Building.class, "楼栋信息");
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
        return super.importExcel(request, response, Building.class);
    }

}
