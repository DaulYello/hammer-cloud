package com.fmkj.user.server.controller;

import com.fmkj.user.dao.domain.BmList;
import com.fmkj.user.server.service.BmListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 *  黑名单
 */
@RestController
@RequestMapping("/bmList")
public class BmListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BmListController.class);

    @Autowired
    private BmListService bmListService;

    /**
     * 判断用户是否黑名单
     */
    @GetMapping("/isBlacker")
    public Boolean isBlacker(@RequestParam HashMap<String, Object> params) {
        List<BmList> list = bmListService.selectByMap(params);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

}
