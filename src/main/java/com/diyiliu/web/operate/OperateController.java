package com.diyiliu.web.operate;

import com.diyiliu.web.operate.facade.DeployJpa;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description: OperateController
 * Author: DIYILIU
 * Update: 2018-05-26 11:27
 */
@RestController
@RequestMapping("/opt")
public class OperateController {

    @Resource
    private DeployJpa deployJpa;


}
