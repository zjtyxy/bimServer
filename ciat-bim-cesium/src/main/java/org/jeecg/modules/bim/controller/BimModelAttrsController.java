package org.jeecg.modules.bim.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.bim.entity.BimModelAttrs;
import org.jeecg.modules.bim.entity.BimModelAttrsCategories;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesPropsService;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesService;
import org.jeecg.modules.bim.service.IBimModelAttrsService;

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
 * @Description: 模型属性
 * @Author: jeecg-boot
 * @Date: 2021-12-20
 * @Version: V1.0
 */
@Api(tags = "模型属性")
@RestController
@RequestMapping("/bim/bimModelAttrs")
@Slf4j
public class BimModelAttrsController extends JeecgController<BimModelAttrs, IBimModelAttrsService> {
    @Autowired
    private IBimModelAttrsService bimModelAttrsService;
    @Autowired
    private IBimModelAttrsCategoriesService  bimModelAttrsCategoriesService;
    @Autowired
    private IBimModelAttrsCategoriesPropsService bimModelAttrsCategoriesPropsService;

    /**
     * 分页列表查询
     *
     * @param bimModelAttrs
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "模型属性-分页列表查询")
    @ApiOperation(value = "模型属性-分页列表查询", notes = "模型属性-分页列表查询")
    @GetMapping(value = "/rootList")

    public Result<?> queryPageList(BimModelAttrs bimModelAttrs,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        String hasQuery = req.getParameter("hasQuery");
        if (hasQuery != null && "true".equals(hasQuery)) {
            QueryWrapper<BimModelAttrs> queryWrapper = QueryGenerator.initQueryWrapper(bimModelAttrs, req.getParameterMap());
            List<BimModelAttrs> list = bimModelAttrsService.queryTreeListNoPage(queryWrapper);
            IPage<BimModelAttrs> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } else {
            String parentId = bimModelAttrs.getParentId();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "-1";
            }
            bimModelAttrs.setParentId(null);
            QueryWrapper<BimModelAttrs> queryWrapper = QueryGenerator.initQueryWrapper(bimModelAttrs, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("parent_id", parentId);
            Page<BimModelAttrs> page = new Page<BimModelAttrs>(pageNo, pageSize);
            IPage<BimModelAttrs> pageList = bimModelAttrsService.page(page, queryWrapper);
            return Result.OK(pageList);
        }
    }

    /**
     * 获取子数据
     *
     * @param bimModelAttrs
     * @param req
     * @return
     */
    @AutoLog(value = "模型属性-获取子数据")
    @ApiOperation(value = "模型属性-获取子数据", notes = "模型属性-获取子数据")
    @GetMapping(value = "/childList")
    public Result<?> queryPageList(BimModelAttrs bimModelAttrs, HttpServletRequest req) {
        QueryWrapper<BimModelAttrs> queryWrapper = QueryGenerator.initQueryWrapper(bimModelAttrs, req.getParameterMap());
        List<BimModelAttrs> list = bimModelAttrsService.list(queryWrapper);
        IPage<BimModelAttrs> pageList = new Page<>(1, 10, list.size());
        pageList.setRecords(list);
        return Result.OK(pageList);
    }

    /**
     * 批量查询子节点
     *
     * @param parentIds 父ID（多个采用半角逗号分割）
     * @param parentIds
     * @return 返回 IPage
     * @return
     */
    @AutoLog(value = "模型属性-批量获取子数据")
    @ApiOperation(value = "模型属性-批量获取子数据", notes = "模型属性-批量获取子数据")
    @GetMapping("/getChildListBatch")
    public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
        try {
            QueryWrapper<BimModelAttrs> queryWrapper = new QueryWrapper<>();
            List<String> parentIdList = Arrays.asList(parentIds.split(","));
            queryWrapper.in("parent_id", parentIdList);
            List<BimModelAttrs> list = bimModelAttrsService.list(queryWrapper);
            IPage<BimModelAttrs> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("批量查询子节点失败：" + e.getMessage());
        }
    }

    /**
     * 添加
     *
     * @param bimModelAttrs
     * @return
     */
    @AutoLog(value = "模型属性-添加")
    @ApiOperation(value = "模型属性-添加", notes = "模型属性-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BimModelAttrs bimModelAttrs) {
        bimModelAttrsService.addBimModelAttrs(bimModelAttrs);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param bimModelAttrs
     * @return
     */
    @AutoLog(value = "模型属性-编辑")
    @ApiOperation(value = "模型属性-编辑", notes = "模型属性-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody BimModelAttrs bimModelAttrs) {
        bimModelAttrsService.updateBimModelAttrs(bimModelAttrs);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "模型属性-通过id删除")
    @ApiOperation(value = "模型属性-通过id删除", notes = "模型属性-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        bimModelAttrsService.deleteBimModelAttrs(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "模型属性-批量删除")
    @ApiOperation(value = "模型属性-批量删除", notes = "模型属性-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.bimModelAttrsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "模型属性-通过id查询")
    @ApiOperation(value = "模型属性-通过id查询", notes = "模型属性-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        BimModelAttrs bimModelAttrs = bimModelAttrsService.getById(id);
        if (bimModelAttrs == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(bimModelAttrs);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param bimModelAttrs
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BimModelAttrs bimModelAttrs) {
        return super.exportXls(request, bimModelAttrs, BimModelAttrs.class, "模型属性");
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
            try {

                JSONObject jsonfile = JSON.parseObject(new String(file.getBytes()));
                JSONObject jd = jsonfile.getJSONObject("data");
                List<BimModelAttrs> bmal = new ArrayList<>();
                List<BimModelAttrsCategories> categorieslist = new ArrayList<>();
                for (String key : jd.keySet()) {
                    BimModelAttrs bma = jd.getObject(key, BimModelAttrs.class);
                    JSONArray categories = jd.getJSONObject(key).getJSONArray("categories");
                    for (int i = 0; i < categories.size(); i++) {
                        BimModelAttrsCategories categorty = categories.getObject(i, BimModelAttrsCategories.class);
                        categorty.setMainId(bma.getExternalId());
                        bimModelAttrsCategoriesService.save(categorty);
                        categorty.getProps().setMainId(categorty.getId());
                        bimModelAttrsCategoriesPropsService.save(categorty.getProps());
                    }
                    bmal.add(bma);
                }
                bimModelAttrsService.saveBatch(bmal);

               // UPDATE bim_model_attrs set has_child ='1' where db_id IN (select a.pid from (select distinct parent_id pid from bim_model_attrs) a)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.OK();
    }

}
