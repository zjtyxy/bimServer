package org.jeecg.modules.bim.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import org.jeecg.modules.bim.entity.BimModelAttrsCategories;
import org.jeecg.modules.bim.vo.BimModelAttrsCategoriesPage;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesService;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesPropsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 模型参数属性类别
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
@Api(tags="模型参数属性类别")
@RestController
@RequestMapping("/bim/bimModelAttrsCategories")
@Slf4j
public class BimModelAttrsCategoriesController {
	@Autowired
	private IBimModelAttrsCategoriesService bimModelAttrsCategoriesService;
	@Autowired
	private IBimModelAttrsCategoriesPropsService bimModelAttrsCategoriesPropsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param bimModelAttrsCategories
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-分页列表查询")
	@ApiOperation(value="模型参数属性类别-分页列表查询", notes="模型参数属性类别-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BimModelAttrsCategories bimModelAttrsCategories,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BimModelAttrsCategories> queryWrapper = QueryGenerator.initQueryWrapper(bimModelAttrsCategories, req.getParameterMap());
		Page<BimModelAttrsCategories> page = new Page<BimModelAttrsCategories>(pageNo, pageSize);
		IPage<BimModelAttrsCategories> pageList = bimModelAttrsCategoriesService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param bimModelAttrsCategoriesPage
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-添加")
	@ApiOperation(value="模型参数属性类别-添加", notes="模型参数属性类别-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BimModelAttrsCategoriesPage bimModelAttrsCategoriesPage) {
		BimModelAttrsCategories bimModelAttrsCategories = new BimModelAttrsCategories();
		BeanUtils.copyProperties(bimModelAttrsCategoriesPage, bimModelAttrsCategories);
		bimModelAttrsCategoriesService.saveMain(bimModelAttrsCategories, bimModelAttrsCategoriesPage.getBimModelAttrsCategoriesPropsList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param bimModelAttrsCategoriesPage
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-编辑")
	@ApiOperation(value="模型参数属性类别-编辑", notes="模型参数属性类别-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BimModelAttrsCategoriesPage bimModelAttrsCategoriesPage) {
		BimModelAttrsCategories bimModelAttrsCategories = new BimModelAttrsCategories();
		BeanUtils.copyProperties(bimModelAttrsCategoriesPage, bimModelAttrsCategories);
		BimModelAttrsCategories bimModelAttrsCategoriesEntity = bimModelAttrsCategoriesService.getById(bimModelAttrsCategories.getId());
		if(bimModelAttrsCategoriesEntity==null) {
			return Result.error("未找到对应数据");
		}
		bimModelAttrsCategoriesService.updateMain(bimModelAttrsCategories, bimModelAttrsCategoriesPage.getBimModelAttrsCategoriesPropsList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-通过id删除")
	@ApiOperation(value="模型参数属性类别-通过id删除", notes="模型参数属性类别-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bimModelAttrsCategoriesService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-批量删除")
	@ApiOperation(value="模型参数属性类别-批量删除", notes="模型参数属性类别-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bimModelAttrsCategoriesService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "模型参数属性类别-通过id查询")
	@ApiOperation(value="模型参数属性类别-通过id查询", notes="模型参数属性类别-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BimModelAttrsCategories bimModelAttrsCategories = bimModelAttrsCategoriesService.getById(id);
		if(bimModelAttrsCategories==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(bimModelAttrsCategories);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "模型属性类别属性通过主表ID查询")
	@ApiOperation(value="模型属性类别属性主表ID查询", notes="模型属性类别属性-通主表ID查询")
	@GetMapping(value = "/queryBimModelAttrsCategoriesPropsByMainId")
	public Result<?> queryBimModelAttrsCategoriesPropsListByMainId(@RequestParam(name="id",required=true) String id) {
		List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList = bimModelAttrsCategoriesPropsService.selectByMainId(id);
		return Result.OK(bimModelAttrsCategoriesPropsList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param bimModelAttrsCategories
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BimModelAttrsCategories bimModelAttrsCategories) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<BimModelAttrsCategories> queryWrapper = QueryGenerator.initQueryWrapper(bimModelAttrsCategories, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<BimModelAttrsCategories> queryList = bimModelAttrsCategoriesService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<BimModelAttrsCategories> bimModelAttrsCategoriesList = new ArrayList<BimModelAttrsCategories>();
      if(oConvertUtils.isEmpty(selections)) {
          bimModelAttrsCategoriesList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          bimModelAttrsCategoriesList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<BimModelAttrsCategoriesPage> pageList = new ArrayList<BimModelAttrsCategoriesPage>();
      for (BimModelAttrsCategories main : bimModelAttrsCategoriesList) {
          BimModelAttrsCategoriesPage vo = new BimModelAttrsCategoriesPage();
          BeanUtils.copyProperties(main, vo);
          List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList = bimModelAttrsCategoriesPropsService.selectByMainId(main.getId());
          vo.setBimModelAttrsCategoriesPropsList(bimModelAttrsCategoriesPropsList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "模型参数属性类别列表");
      mv.addObject(NormalExcelConstants.CLASS, BimModelAttrsCategoriesPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("模型参数属性类别数据", "导出人:"+sysUser.getRealname(), "模型参数属性类别"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<BimModelAttrsCategoriesPage> list = ExcelImportUtil.importExcel(file.getInputStream(), BimModelAttrsCategoriesPage.class, params);
              for (BimModelAttrsCategoriesPage page : list) {
                  BimModelAttrsCategories po = new BimModelAttrsCategories();
                  BeanUtils.copyProperties(page, po);
                  bimModelAttrsCategoriesService.saveMain(po, page.getBimModelAttrsCategoriesPropsList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
