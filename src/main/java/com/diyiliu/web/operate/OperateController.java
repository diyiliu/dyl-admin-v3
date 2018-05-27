package com.diyiliu.web.operate;

import com.diyiliu.web.operate.dto.Deploy;
import com.diyiliu.web.operate.facade.DeployJpa;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/deployList")
    public Map deployList(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam(required = false) String search) {
        Sort sort = new Sort(new Sort.Order[]{new Sort.Order("address")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Deploy> deployPage;
        if (StringUtils.isEmpty(search)) {
            deployPage = deployJpa.findAll(pageable);
        } else {
            String like = "%" + search + "%";
            deployPage = deployJpa.findByAddressLikeOrPlatformLikeOrTagLike(like, like, like, pageable);
        }

        Map respMap = new HashMap();
        respMap.put("data", deployPage.getContent());
        respMap.put("total", deployPage.getTotalElements());

        return respMap;
    }

    @PostMapping("/deploy")
    public Integer saveDeploy(Deploy deploy) {
        String user = (String) SecurityUtils.getSubject().getPrincipal();

        deploy.setCreateUser(user);
        deploy.setCreateTime(new Date());
        deploy = deployJpa.save(deploy);
        if (deploy == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/deploy")
    public Integer modifyDeploy(Deploy deploy) {
        Deploy temp = deployJpa.findById(deploy.getId());

        deploy.setCreateUser(temp.getCreateUser());
        deploy.setCreateTime(temp.getCreateTime());
        temp = deployJpa.save(deploy);
        if (temp == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/deploy")
    public Integer deleteDeploy(@RequestBody List<Long> ids) {
        deployJpa.deleteByIdIn(ids);

        return 1;
    }

}
