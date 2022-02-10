package org.jeecg.modules.rule.controller;

import org.jeecg.common.system.query.QueryGenerator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rule.entity.RuleNode;
import org.jeecg.modules.rule.entity.NodeRelation;
import org.jeecg.modules.rule.entity.RuleChain;
import org.jeecg.modules.rule.service.IRuleChainService;
import org.jeecg.modules.rule.service.IRuleNodeService;
import org.jeecg.modules.rule.service.INodeRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 /**
 * @Description: 规则链
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
@Api(tags="规则链")
@RestController
@RequestMapping("/rule/ruleChain")
@Slf4j
public class RuleChainController extends JeecgController<RuleChain, IRuleChainService> {

	@Autowired
	private IRuleChainService ruleChainService;

	@Autowired
	private IRuleNodeService ruleNodeService;

	@Autowired
	private INodeRelationService nodeRelationService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
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
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "规则链-通过id删除")
    @ApiOperation(value="规则链-通过id删除", notes="规则链-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        ruleChainService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "规则链-批量删除")
    @ApiOperation(value="规则链-批量删除", notes="规则链-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.ruleChainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RuleChain ruleChain) {
        return super.exportXls(request, ruleChain, RuleChain.class, "规则链");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RuleChain.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/
	

    /*--------------------------------子表处理-规则节点-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "规则节点-通过主表ID查询")
	@ApiOperation(value="规则节点-通过主表ID查询", notes="规则节点-通过主表ID查询")
	@GetMapping(value = "/listRuleNodeByMainId")
    public Result<?> listRuleNodeByMainId(RuleNode ruleNode,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<RuleNode> queryWrapper = QueryGenerator.initQueryWrapper(ruleNode, req.getParameterMap());
        Page<RuleNode> page = new Page<RuleNode>(pageNo, pageSize);
        IPage<RuleNode> pageList = ruleNodeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param ruleNode
	 * @return
	 */
	@AutoLog(value = "规则节点-添加")
	@ApiOperation(value="规则节点-添加", notes="规则节点-添加")
	@PostMapping(value = "/addRuleNode")
	public Result<?> addRuleNode(@RequestBody RuleNode ruleNode) {
		ruleNodeService.save(ruleNode);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param ruleNode
	 * @return
	 */
	@AutoLog(value = "规则节点-编辑")
	@ApiOperation(value="规则节点-编辑", notes="规则节点-编辑")
	@PutMapping(value = "/editRuleNode")
	public Result<?> editRuleNode(@RequestBody RuleNode ruleNode) {
		ruleNodeService.updateById(ruleNode);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "规则节点-通过id删除")
	@ApiOperation(value="规则节点-通过id删除", notes="规则节点-通过id删除")
	@DeleteMapping(value = "/deleteRuleNode")
	public Result<?> deleteRuleNode(@RequestParam(name="id",required=true) String id) {
		ruleNodeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "规则节点-批量删除")
	@ApiOperation(value="规则节点-批量删除", notes="规则节点-批量删除")
	@DeleteMapping(value = "/deleteBatchRuleNode")
	public Result<?> deleteBatchRuleNode(@RequestParam(name="ids",required=true) String ids) {
	    this.ruleNodeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportRuleNode")
    public ModelAndView exportRuleNode(HttpServletRequest request, RuleNode ruleNode) {
		 // Step.1 组装查询条件
		 QueryWrapper<RuleNode> queryWrapper = QueryGenerator.initQueryWrapper(ruleNode, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<RuleNode> pageList = ruleNodeService.list(queryWrapper);
		 List<RuleNode> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "规则节点"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, RuleNode.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("规则节点报表", "导出人:" + sysUser.getRealname(), "规则节点"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importRuleNode/{mainId}")
    public Result<?> importRuleNode(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<RuleNode> list = ExcelImportUtil.importExcel(file.getInputStream(), RuleNode.class, params);
				 for (RuleNode temp : list) {
                    temp.setRuleChainId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 ruleNodeService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-规则节点-end----------------------------------------------*/

    /*--------------------------------子表处理-节点连接-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "节点连接-通过主表ID查询")
	@ApiOperation(value="节点连接-通过主表ID查询", notes="节点连接-通过主表ID查询")
	@GetMapping(value = "/listNodeRelationByMainId")
    public Result<?> listNodeRelationByMainId(NodeRelation nodeRelation,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<NodeRelation> queryWrapper = QueryGenerator.initQueryWrapper(nodeRelation, req.getParameterMap());
        Page<NodeRelation> page = new Page<NodeRelation>(pageNo, pageSize);
        IPage<NodeRelation> pageList = nodeRelationService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param nodeRelation
	 * @return
	 */
	@AutoLog(value = "节点连接-添加")
	@ApiOperation(value="节点连接-添加", notes="节点连接-添加")
	@PostMapping(value = "/addNodeRelation")
	public Result<?> addNodeRelation(@RequestBody NodeRelation nodeRelation) {
		nodeRelationService.save(nodeRelation);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param nodeRelation
	 * @return
	 */
	@AutoLog(value = "节点连接-编辑")
	@ApiOperation(value="节点连接-编辑", notes="节点连接-编辑")
	@PutMapping(value = "/editNodeRelation")
	public Result<?> editNodeRelation(@RequestBody NodeRelation nodeRelation) {
		nodeRelationService.updateById(nodeRelation);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "节点连接-通过id删除")
	@ApiOperation(value="节点连接-通过id删除", notes="节点连接-通过id删除")
	@DeleteMapping(value = "/deleteNodeRelation")
	public Result<?> deleteNodeRelation(@RequestParam(name="id",required=true) String id) {
		nodeRelationService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "节点连接-批量删除")
	@ApiOperation(value="节点连接-批量删除", notes="节点连接-批量删除")
	@DeleteMapping(value = "/deleteBatchNodeRelation")
	public Result<?> deleteBatchNodeRelation(@RequestParam(name="ids",required=true) String ids) {
	    this.nodeRelationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportNodeRelation")
    public ModelAndView exportNodeRelation(HttpServletRequest request, NodeRelation nodeRelation) {
		 // Step.1 组装查询条件
		 QueryWrapper<NodeRelation> queryWrapper = QueryGenerator.initQueryWrapper(nodeRelation, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<NodeRelation> pageList = nodeRelationService.list(queryWrapper);
		 List<NodeRelation> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
		 if (oConvertUtils.isNotEmpty(selections)) {
			 List<String> selectionList = Arrays.asList(selections.split(","));
			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
		 } else {
			 exportList = pageList;
		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "节点连接"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, NodeRelation.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("节点连接报表", "导出人:" + sysUser.getRealname(), "节点连接"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importNodeRelation/{mainId}")
    public Result<?> importNodeRelation(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<NodeRelation> list = ExcelImportUtil.importExcel(file.getInputStream(), NodeRelation.class, params);
				 for (NodeRelation temp : list) {
                    temp.setMainId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 nodeRelationService.saveBatch(list);
				 log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
				 return Result.OK("文件导入成功！数据行数：" + list.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(), e);
				 return Result.error("文件导入失败:" + e.getMessage());
			 } finally {
				 try {
					 file.getInputStream().close();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 }
		 }
		 return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-节点连接-end----------------------------------------------*/




}
