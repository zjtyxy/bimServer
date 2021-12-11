package org.jeecg.modules.bim.controller;

import org.jeecg.modules.bim.entity.BimModel;
import org.jeecg.modules.bim.service.IBimModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/bim/bimShow")
public class CiatBIMShowController {

    @Autowired
    private IBimModelService bimModelService;
    @RequestMapping("/3dtShow")
    public ModelAndView show3dt(@RequestParam String id, ModelAndView modelAndView) {
        BimModel bb = bimModelService.getById(id);
        modelAndView.addObject("bimModel",bb);
        modelAndView.setViewName("/bim/bimShow");
        return modelAndView;
    }
    @RequestMapping("/3dtListShow")
    public ModelAndView show3dtList(@RequestParam String ids, ModelAndView modelAndView) {
        List<BimModel> bbl = bimModelService.listByIds(Arrays.asList(ids.split(",")));
        modelAndView.addObject("bimModelList",bbl);
        modelAndView.setViewName("/bim/bimListShower");
        return modelAndView;
    }
}
