package com.tsis.hello.controller;

import com.tsis.hello.domain.TbFoodOrderHist;
import com.tsis.hello.domain.TbFoodOrderInfo;
import com.tsis.hello.service.TbFoodOrderInfoService;
import com.tsis.hello.service.TbFoodOrderHistService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TbFoodOrderController {

    @SuppressWarnings("unused")
    private final TbFoodOrderInfoService tbFoodOrderInfoService;
    private final TbFoodOrderHistService tbFoodOrderHistService;

    @Autowired
    public TbFoodOrderController(TbFoodOrderInfoService tbFoodOrderInfoService, TbFoodOrderHistService tbFoodOrderHistService) {
        this.tbFoodOrderInfoService = tbFoodOrderInfoService;
        this.tbFoodOrderHistService = tbFoodOrderHistService;
    }

    @GetMapping("/salad/hist/new")
    public String createHistForm() {
        return "cms/createTbFoodOrderHistForm";
    }
    
    @GetMapping("/salad/info/new")
    public String createForm() {
        return "cms/createTbFoodOrderInfoForm";
    }

    @PostMapping("/salad/hist/new")
    public String createHist(TbFoodOrderHistForm form) {
        TbFoodOrderHist tbFoodOrderHist = new TbFoodOrderHist();
        tbFoodOrderHist.setOrderNo(form.getOrderNo());
        tbFoodOrderHist.setName(form.getName());
        tbFoodOrderHist.setId(form.getId());
        tbFoodOrderHist.setDept(form.getDept());
        tbFoodOrderHist.setOrderMenu(form.getOrderMenu());
        tbFoodOrderHist.setOrderCnt(form.getOrderCnt());
        tbFoodOrderHist.setCreatedAt(form.getCreatedAt());
        tbFoodOrderHist.setOrderStatus(form.getOrderStatus());
        tbFoodOrderHist.setUpdatedAt(form.getUpdatedAt());

        tbFoodOrderHistService.join(tbFoodOrderHist);
        return "redirect:/salad";
    }
    

    @PostMapping("/salad/info/new")
    public String create(TbFoodOrderInfoForm form) {
        TbFoodOrderInfo tbFoodOrderInfo = new TbFoodOrderInfo();
        tbFoodOrderInfo.setReceiveDate(form.getReceiveDate());
        // tbFoodOrderInfo.setWeekMenuImg(form.getWeekMenuImg());
        // Handle file upload
        MultipartFile file = form.getWeekMenuImg();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            // ... logic to save the file (e.g., to a specific directory) ...
            tbFoodOrderInfo.setWeekMenuImg(fileName); // Store the filename in the database
        }
        tbFoodOrderInfo.setMenuACnt(form.getMenuACnt());
        tbFoodOrderInfo.setMenuBCnt(form.getMenuBCnt());
        tbFoodOrderInfo.setCreatedAt(form.getCreatedAt());
        tbFoodOrderInfo.setUpdatedAt(form.getUpdatedAt());

        tbFoodOrderInfoService.join(tbFoodOrderInfo);
        return "redirect:/salad";
    }

    // @GetMapping("/salad/infoLists")
    // public Model getInfoLists(Model model) {
    //     List<TbFoodOrderInfo> tbFoodOrderInfos = tbFoodOrderInfoService.findTbFoodOrderInfos();
    //     model.addAttribute("tbFoodOrderInfos", tbFoodOrderInfos);
    //     return model;
    // }

    @GetMapping("/salad/orderInfos")
    public String list(Model model) {
        List<TbFoodOrderInfo> tbFoodOrderInfos = tbFoodOrderInfoService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderInfos", tbFoodOrderInfos);
        return "/cms/saladOrderInfos";
    }
    
    @GetMapping("/salad/orderHists")
    public String listHist(Model model) {
        List<TbFoodOrderHist> tbFoodOrderHists = tbFoodOrderHistService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderHists", tbFoodOrderHists);
        return "/cms/saladOrderHists";
    }
    
    
}
