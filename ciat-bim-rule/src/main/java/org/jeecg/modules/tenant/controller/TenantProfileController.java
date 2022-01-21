package org.jeecg.modules.tenant.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantProfileService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 住户配置
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Api(tags="住户配置")
@RestController
@RequestMapping("/modules.tenant/tenantProfile")
@Slf4j
public class TenantProfileController extends JeecgController<TenantProfile, ITenantProfileService> {
	@Autowired
	private ITenantProfileService tenantProfileService;

	/**
	 * 分页列表查询
	 *
	 * @param tenantProfile
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "住户配置-分页列表查询")
	@ApiOperation(value="住户配置-分页列表查询", notes="住户配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TenantProfile tenantProfile,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TenantProfile> queryWrapper = QueryGenerator.initQueryWrapper(tenantProfile, req.getParameterMap());
		Page<TenantProfile> page = new Page<TenantProfile>(pageNo, pageSize);
		IPage<TenantProfile> pageList = tenantProfileService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param tenantProfile
	 * @return
	 */
	@AutoLog(value = "住户配置-添加")
	@ApiOperation(value="住户配置-添加", notes="住户配置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TenantProfile tenantProfile) {
		tenantProfileService.save(tenantProfile);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param tenantProfile
	 * @return
	 */
	@AutoLog(value = "住户配置-编辑")
	@ApiOperation(value="住户配置-编辑", notes="住户配置-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TenantProfile tenantProfile) {
		tenantProfileService.updateById(tenantProfile);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "住户配置-通过id删除")
	@ApiOperation(value="住户配置-通过id删除", notes="住户配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		tenantProfileService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "住户配置-批量删除")
	@ApiOperation(value="住户配置-批量删除", notes="住户配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tenantProfileService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "住户配置-通过id查询")
	@ApiOperation(value="住户配置-通过id查询", notes="住户配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TenantProfile tenantProfile = tenantProfileService.getById(id);
		if(tenantProfile==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(tenantProfile);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param tenantProfile
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TenantProfile tenantProfile) {
        return super.exportXls(request, tenantProfile, TenantProfile.class, "住户配置");
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
        return super.importExcel(request, response, TenantProfile.class);
    }

}
