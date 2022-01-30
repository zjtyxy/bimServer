package org.jeecg.modules.rule.controller;

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
import org.jeecg.modules.rule.entity.RuleChain;
import org.jeecg.modules.rule.service.IRuleChainService;

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
 * @Description: 规则链
 * @Author: jeecg-boot
 * @Date:   2022-01-27
 * @Version: V1.0
 */
@Api(tags="规则链")
@RestController
@RequestMapping("/rule/ruleChain")
@Slf4j
public class RuleChainController extends JeecgController<RuleChain, IRuleChainService> {
	@Autowired
	private IRuleChainService ruleChainService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ruleChain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "规则链-分页列表查询")
	@ApiOperation(value="规则链-分页列表查询", notes="规则链-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RuleChain ruleChain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RuleChain> queryWrapper = QueryGenerator.initQueryWrapper(ruleChain, req.getParameterMap());
		Page<RuleChain> page = new Page<RuleChain>(pageNo, pageSize);
		IPage<RuleChain> pageList = ruleChainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ruleChain
	 * @return
	 */
	@AutoLog(value = "规则链-添加")
	@ApiOperation(value="规则链-添加", notes="规则链-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RuleChain ruleChain) {
		ruleChainService.save(ruleChain);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ruleChain
	 * @return
	 */
	@AutoLog(value = "规则链-编辑")
	@ApiOperation(value="规则链-编辑", notes="规则链-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RuleChain ruleChain) {
		ruleChainService.updateById(ruleChain);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "规则链-通过id删除")
	@ApiOperation(value="规则链-通过id删除", notes="规则链-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ruleChainService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "规则链-批量删除")
	@ApiOperation(value="规则链-批量删除", notes="规则链-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ruleChainService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "规则链-通过id查询")
	@ApiOperation(value="规则链-通过id查询", notes="规则链-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RuleChain ruleChain = ruleChainService.getById(id);
		if(ruleChain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ruleChain);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ruleChain
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RuleChain ruleChain) {
        return super.exportXls(request, ruleChain, RuleChain.class, "规则链");
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
        return super.importExcel(request, response, RuleChain.class);
    }

}
