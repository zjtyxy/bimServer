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
import org.jeecg.modules.rule.entity.RuleNodeState;
import org.jeecg.modules.rule.service.IRuleNodeStateService;

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
 * @Description: 规则节点状态
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
@Api(tags="规则节点状态")
@RestController
@RequestMapping("/rule/ruleNodeState")
@Slf4j
public class RuleNodeStateController extends JeecgController<RuleNodeState, IRuleNodeStateService> {
	@Autowired
	private IRuleNodeStateService ruleNodeStateService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ruleNodeState
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "规则节点状态-分页列表查询")
	@ApiOperation(value="规则节点状态-分页列表查询", notes="规则节点状态-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RuleNodeState ruleNodeState,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RuleNodeState> queryWrapper = QueryGenerator.initQueryWrapper(ruleNodeState, req.getParameterMap());
		Page<RuleNodeState> page = new Page<RuleNodeState>(pageNo, pageSize);
		IPage<RuleNodeState> pageList = ruleNodeStateService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ruleNodeState
	 * @return
	 */
	@AutoLog(value = "规则节点状态-添加")
	@ApiOperation(value="规则节点状态-添加", notes="规则节点状态-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RuleNodeState ruleNodeState) {
		ruleNodeStateService.save(ruleNodeState);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ruleNodeState
	 * @return
	 */
	@AutoLog(value = "规则节点状态-编辑")
	@ApiOperation(value="规则节点状态-编辑", notes="规则节点状态-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RuleNodeState ruleNodeState) {
		ruleNodeStateService.updateById(ruleNodeState);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "规则节点状态-通过id删除")
	@ApiOperation(value="规则节点状态-通过id删除", notes="规则节点状态-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		ruleNodeStateService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "规则节点状态-批量删除")
	@ApiOperation(value="规则节点状态-批量删除", notes="规则节点状态-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ruleNodeStateService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "规则节点状态-通过id查询")
	@ApiOperation(value="规则节点状态-通过id查询", notes="规则节点状态-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RuleNodeState ruleNodeState = ruleNodeStateService.getById(id);
		if(ruleNodeState==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ruleNodeState);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ruleNodeState
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RuleNodeState ruleNodeState) {
        return super.exportXls(request, ruleNodeState, RuleNodeState.class, "规则节点状态");
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
        return super.importExcel(request, response, RuleNodeState.class);
    }

}
