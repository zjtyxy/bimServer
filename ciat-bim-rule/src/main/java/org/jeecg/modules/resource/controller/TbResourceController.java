package org.jeecg.modules.resource.controller;

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
import org.jeecg.modules.resource.entity.TbResource;
import org.jeecg.modules.resource.service.ITbResourceService;

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
 * @Description: TB资源表
 * @Author: jeecg-boot
 * @Date:   2022-01-24
 * @Version: V1.0
 */
@Api(tags="TB资源表")
@RestController
@RequestMapping("/resource/tbResource")
@Slf4j
public class TbResourceController extends JeecgController<TbResource, ITbResourceService> {
	@Autowired
	private ITbResourceService tbResourceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tbResource
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "TB资源表-分页列表查询")
	@ApiOperation(value="TB资源表-分页列表查询", notes="TB资源表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TbResource tbResource,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TbResource> queryWrapper = QueryGenerator.initQueryWrapper(tbResource, req.getParameterMap());
		Page<TbResource> page = new Page<TbResource>(pageNo, pageSize);
		IPage<TbResource> pageList = tbResourceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tbResource
	 * @return
	 */
	@AutoLog(value = "TB资源表-添加")
	@ApiOperation(value="TB资源表-添加", notes="TB资源表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TbResource tbResource) {
		tbResourceService.save(tbResource);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tbResource
	 * @return
	 */
	@AutoLog(value = "TB资源表-编辑")
	@ApiOperation(value="TB资源表-编辑", notes="TB资源表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TbResource tbResource) {
		tbResourceService.updateById(tbResource);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "TB资源表-通过id删除")
	@ApiOperation(value="TB资源表-通过id删除", notes="TB资源表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tbResourceService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "TB资源表-批量删除")
	@ApiOperation(value="TB资源表-批量删除", notes="TB资源表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tbResourceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "TB资源表-通过id查询")
	@ApiOperation(value="TB资源表-通过id查询", notes="TB资源表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TbResource tbResource = tbResourceService.getById(id);
		if(tbResource==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(tbResource);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tbResource
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TbResource tbResource) {
        return super.exportXls(request, tbResource, TbResource.class, "TB资源表");
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
        return super.importExcel(request, response, TbResource.class);
    }

}
