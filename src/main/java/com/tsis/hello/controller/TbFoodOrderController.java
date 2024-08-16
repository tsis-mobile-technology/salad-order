package com.tsis.hello.controller;

import com.tsis.hello.domain.TbFoodOrderHist;
import com.tsis.hello.domain.TbFoodOrderInfo;
import com.tsis.hello.service.TbFoodOrderInfoService;
import com.tsis.hello.service.TbFoodOrderHistService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String UPLOAD_DIR = "/Users/gotaejong/ExternHard/97_Workspace/tsis/upload/"; // 파일 저장 경로
    @Value("${upload.dir}")
    private String uploadDir;

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
    public String create(TbFoodOrderInfoForm form) throws IOException {
        TbFoodOrderInfo tbFoodOrderInfo = new TbFoodOrderInfo();
        tbFoodOrderInfo.setReceiveDate(form.getReceiveDate());
        // tbFoodOrderInfo.setWeekMenuImg(form.getWeekMenuImg());
        // Handle file upload
        MultipartFile file = form.getWeekMenuImg();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
                        // 파일 저장
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, bytes);

            tbFoodOrderInfo.setWeekMenuImg(fileName); // Store the filename in the database
        }
        tbFoodOrderInfo.setMenuACnt(form.getMenuACnt());
        tbFoodOrderInfo.setMenuBCnt(form.getMenuBCnt());
        tbFoodOrderInfo.setCreatedAt(form.getCreatedAt());
        tbFoodOrderInfo.setUpdatedAt(form.getUpdatedAt());

        tbFoodOrderInfoService.join(tbFoodOrderInfo);
        return "redirect:/salad"; // Now this will redirect correctly
    }

    // @GetMapping("/salad/infoLists")
    // public Model getInfoLists(Model model) {
    //     List<TbFoodOrderInfo> tbFoodOrderInfos = tbFoodOrderInfoService.findTbFoodOrderInfos();
    //     model.addAttribute("tbFoodOrderInfos", tbFoodOrderInfos);
    //     return model;
    // }

    @GetMapping("/salad/orderInfos")
    public String listInfo(Model model, TbFoodOrderInfoForm form) {
        List<TbFoodOrderInfo> tbFoodOrderInfos = tbFoodOrderInfoService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderInfos", tbFoodOrderInfos);
        model.addAttribute("totalCount", tbFoodOrderInfos.size());
        model.addAttribute("searchType", form.getSearch_type());
        model.addAttribute("searchValue", form.getSearch_value());
        model.addAttribute("date_start", form.getDate_start());
        model.addAttribute("date_end", form.getDate_end());

        return "/cms/saladOrderInfos";
    }

    @PostMapping("/salad/orderInfos")
    public String listSearchInfo(Model model, TbFoodOrderInfoForm form) {
        /**
         * form.getSearch_type(), form.getSearch_value()를 가져와서 데이터 검색 처리 필요
         */
        List<TbFoodOrderInfo> tbFoodOrderInfos = tbFoodOrderInfoService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderInfos", tbFoodOrderInfos);
        model.addAttribute("totalCount", tbFoodOrderInfos.size());
        model.addAttribute("searchType", form.getSearch_type());
        model.addAttribute("searchValue", form.getSearch_value());
        model.addAttribute("date_start", form.getDate_start());
        model.addAttribute("date_end", form.getDate_end());
        
        return "/cms/saladOrderInfos";
    }
    
    @GetMapping("/salad/orderHists")
    public String listHist(Model model, TbFoodOrderHistForm form) {
        List<TbFoodOrderHist> tbFoodOrderHists = tbFoodOrderHistService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderHists", tbFoodOrderHists);
        model.addAttribute("totalCount", tbFoodOrderHists.size());
        //tbFoodOrderHists 리스트에서 orderStatus가 'R' 인것들에 대한 orderCnt 합을 구해 sumCount에 등록
        int sumCount = 0;
        int cancelCount = 0;
        for (TbFoodOrderHist tbFoodOrderHist : tbFoodOrderHists) {
            if (tbFoodOrderHist.getOrderStatus() == 'R') {
                sumCount += tbFoodOrderHist.getOrderCnt();
            }
            else if (tbFoodOrderHist.getOrderStatus() == 'C') {
                cancelCount += tbFoodOrderHist.getOrderCnt();
            }
        }
        model.addAttribute("sumCount", sumCount);
        model.addAttribute("cancelCount", cancelCount);
        model.addAttribute("searchType", form.getSearch_type());
        model.addAttribute("searchValue", form.getSearch_value());
        model.addAttribute("date_start", form.getDate_start());
        model.addAttribute("date_end", form.getDate_end());

        return "/cms/saladOrderHists";
    }
    
    @PostMapping("/salad/orderHists")
    public String listSearchHist(Model model, TbFoodOrderHistForm form) {
        /**
         * form.getSearch_type(), form.getSearch_value()를 가져와서 데이터 검색 처리 필요
         */
        List<TbFoodOrderHist> tbFoodOrderHists = tbFoodOrderHistService.findTbFoodOrderInfos();
        model.addAttribute("tbFoodOrderHists", tbFoodOrderHists);
        model.addAttribute("totalCount", tbFoodOrderHists.size());
        //tbFoodOrderHists 리스트에서 orderStatus가 'R' 인것들에 대한 orderCnt 합을 구해 sumCount에 등록
        int sumCount = 0;
        int cancelCount = 0;
        for (TbFoodOrderHist tbFoodOrderHist : tbFoodOrderHists) {
            if (tbFoodOrderHist.getOrderStatus() == 'R') {
                sumCount += tbFoodOrderHist.getOrderCnt();
            }
            else if (tbFoodOrderHist.getOrderStatus() == 'C') {
                cancelCount += tbFoodOrderHist.getOrderCnt();
            }
        }
        model.addAttribute("sumCount", sumCount);
        model.addAttribute("cancelCount", cancelCount);
        model.addAttribute("searchType", form.getSearch_type());
        model.addAttribute("searchValue", form.getSearch_value());
        model.addAttribute("date_start", form.getDate_start());
        model.addAttribute("date_end", form.getDate_end());

        return "/cms/saladOrderHists";
    }
    
}
