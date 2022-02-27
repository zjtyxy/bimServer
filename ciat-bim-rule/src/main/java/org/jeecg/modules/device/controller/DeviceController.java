package org.jeecg.modules.device.controller;

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
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.DeviceCredentials;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.service.IDeviceService;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.jeecg.modules.device.service.IDeviceCredentialsService;
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
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2022-02-15
 * @Version: V1.0
 */
@Api(tags="设备信息")
@RestController
@RequestMapping("/device/device")
@Slf4j
public class DeviceController extends JeecgController<Device, IDeviceService> {

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private IAttributeKvService attributeKvService;

	@Autowired
	private IDeviceCredentialsService deviceCredentialsService;


	/*---------------------------------主表处理-begin-------------------------------------*/

	/**
	 * 分页列表查询
	 * @param device
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "设备信息-分页列表查询")
	@ApiOperation(value="设备信息-分页列表查询", notes="设备信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Device device,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Device> queryWrapper = QueryGenerator.initQueryWrapper(device, req.getParameterMap());
		Page<Device> page = new Page<Device>(pageNo, pageSize);
		IPage<Device> pageList = deviceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
     *   添加
     * @param device
     * @return
     */
    @AutoLog(value = "设备信息-添加")
    @ApiOperation(value="设备信息-添加", notes="设备信息-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody Device device) {
        deviceService.save(device);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     * @param device
     * @return
     */
    @AutoLog(value = "设备信息-编辑")
    @ApiOperation(value="设备信息-编辑", notes="设备信息-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody Device device) {
        deviceService.updateById(device);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @AutoLog(value = "设备信息-通过id删除")
    @ApiOperation(value="设备信息-通过id删除", notes="设备信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        deviceService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @AutoLog(value = "设备信息-批量删除")
    @ApiOperation(value="设备信息-批量删除", notes="设备信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.deviceService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Device device) {
        return super.exportXls(request, device, Device.class, "设备信息");
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Device.class);
    }
	/*---------------------------------主表处理-end-------------------------------------*/


    /*--------------------------------子表处理-属性表-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "属性表-通过主表ID查询")
	@ApiOperation(value="属性表-通过主表ID查询", notes="属性表-通过主表ID查询")
	@GetMapping(value = "/listAttributeKvByMainId")
    public Result<?> listAttributeKvByMainId(AttributeKv attributeKv,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<AttributeKv> queryWrapper = QueryGenerator.initQueryWrapper(attributeKv, req.getParameterMap());
        Page<AttributeKv> page = new Page<AttributeKv>(pageNo, pageSize);
        IPage<AttributeKv> pageList = attributeKvService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param attributeKv
	 * @return
	 */
	@AutoLog(value = "属性表-添加")
	@ApiOperation(value="属性表-添加", notes="属性表-添加")
	@PostMapping(value = "/addAttributeKv")
	public Result<?> addAttributeKv(@RequestBody AttributeKv attributeKv) {
		attributeKvService.save(attributeKv);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param attributeKv
	 * @return
	 */
	@AutoLog(value = "属性表-编辑")
	@ApiOperation(value="属性表-编辑", notes="属性表-编辑")
	@PutMapping(value = "/editAttributeKv")
	public Result<?> editAttributeKv(@RequestBody AttributeKv attributeKv) {
		attributeKvService.updateByMultiId(attributeKv);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "属性表-通过id删除")
	@ApiOperation(value="属性表-通过id删除", notes="属性表-通过id删除")
	@DeleteMapping(value = "/deleteAttributeKv")
	public Result<?> deleteAttributeKv(@RequestParam(name="id",required=true) String id) {
		//attributeKvService.deleteByMultiId(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "属性表-批量删除")
	@ApiOperation(value="属性表-批量删除", notes="属性表-批量删除")
	@DeleteMapping(value = "/deleteBatchAttributeKv")
	public Result<?> deleteBatchAttributeKv(@RequestParam(name="ids",required=true) String ids) {
	    this.attributeKvService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportAttributeKv")
    public ModelAndView exportAttributeKv(HttpServletRequest request, AttributeKv attributeKv) {
		 // Step.1 组装查询条件
		 QueryWrapper<AttributeKv> queryWrapper = QueryGenerator.initQueryWrapper(attributeKv, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<AttributeKv> pageList = attributeKvService.list(queryWrapper);
		 List<AttributeKv> exportList = null;

		 // 过滤选中数据
		 String selections = request.getParameter("selections");
//		 if (oConvertUtils.isNotEmpty(selections)) {
//			 List<String> selectionList = Arrays.asList(selections.split(","));
//			 exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
//		 } else {
//			 exportList = pageList;
//		 }

		 // Step.3 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 mv.addObject(NormalExcelConstants.FILE_NAME, "属性表"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, AttributeKv.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("属性表报表", "导出人:" + sysUser.getRealname(), "属性表"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importAttributeKv/{mainId}")
    public Result<?> importAttributeKv(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<AttributeKv> list = ExcelImportUtil.importExcel(file.getInputStream(), AttributeKv.class, params);
				 for (AttributeKv temp : list) {
                    temp.setEntityId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 attributeKvService.saveBatch(list);
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

    /*--------------------------------子表处理-属性表-end----------------------------------------------*/

    /*--------------------------------子表处理-认证信息-begin----------------------------------------------*/
	/**
	 * 通过主表ID查询
	 * @return
	 */
	@AutoLog(value = "认证信息-通过主表ID查询")
	@ApiOperation(value="认证信息-通过主表ID查询", notes="认证信息-通过主表ID查询")
	@GetMapping(value = "/listDeviceCredentialsByMainId")
    public Result<?> listDeviceCredentialsByMainId(DeviceCredentials deviceCredentials,
                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    HttpServletRequest req) {
        QueryWrapper<DeviceCredentials> queryWrapper = QueryGenerator.initQueryWrapper(deviceCredentials, req.getParameterMap());
        Page<DeviceCredentials> page = new Page<DeviceCredentials>(pageNo, pageSize);
        IPage<DeviceCredentials> pageList = deviceCredentialsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

	/**
	 * 添加
	 * @param deviceCredentials
	 * @return
	 */
	@AutoLog(value = "认证信息-添加")
	@ApiOperation(value="认证信息-添加", notes="认证信息-添加")
	@PostMapping(value = "/addDeviceCredentials")
	public Result<?> addDeviceCredentials(@RequestBody DeviceCredentials deviceCredentials) {
		deviceCredentialsService.save(deviceCredentials);
		return Result.OK("添加成功！");
	}

    /**
	 * 编辑
	 * @param deviceCredentials
	 * @return
	 */
	@AutoLog(value = "认证信息-编辑")
	@ApiOperation(value="认证信息-编辑", notes="认证信息-编辑")
	@PutMapping(value = "/editDeviceCredentials")
	public Result<?> editDeviceCredentials(@RequestBody DeviceCredentials deviceCredentials) {
		deviceCredentialsService.updateById(deviceCredentials);
		return Result.OK("编辑成功!");
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "认证信息-通过id删除")
	@ApiOperation(value="认证信息-通过id删除", notes="认证信息-通过id删除")
	@DeleteMapping(value = "/deleteDeviceCredentials")
	public Result<?> deleteDeviceCredentials(@RequestParam(name="id",required=true) String id) {
		deviceCredentialsService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "认证信息-批量删除")
	@ApiOperation(value="认证信息-批量删除", notes="认证信息-批量删除")
	@DeleteMapping(value = "/deleteBatchDeviceCredentials")
	public Result<?> deleteBatchDeviceCredentials(@RequestParam(name="ids",required=true) String ids) {
	    this.deviceCredentialsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

    /**
     * 导出
     * @return
     */
    @RequestMapping(value = "/exportDeviceCredentials")
    public ModelAndView exportDeviceCredentials(HttpServletRequest request, DeviceCredentials deviceCredentials) {
		 // Step.1 组装查询条件
		 QueryWrapper<DeviceCredentials> queryWrapper = QueryGenerator.initQueryWrapper(deviceCredentials, request.getParameterMap());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		 // Step.2 获取导出数据
		 List<DeviceCredentials> pageList = deviceCredentialsService.list(queryWrapper);
		 List<DeviceCredentials> exportList = null;

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
		 mv.addObject(NormalExcelConstants.FILE_NAME, "认证信息"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(NormalExcelConstants.CLASS, DeviceCredentials.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("认证信息报表", "导出人:" + sysUser.getRealname(), "认证信息"));
		 mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		 return mv;
    }

    /**
     * 导入
     * @return
     */
    @RequestMapping(value = "/importDeviceCredentials/{mainId}")
    public Result<?> importDeviceCredentials(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		 Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<DeviceCredentials> list = ExcelImportUtil.importExcel(file.getInputStream(), DeviceCredentials.class, params);
				 for (DeviceCredentials temp : list) {
                    temp.setDeviceId(mainId);
				 }
				 long start = System.currentTimeMillis();
				 deviceCredentialsService.saveBatch(list);
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

    /*--------------------------------子表处理-认证信息-end----------------------------------------------*/




}
