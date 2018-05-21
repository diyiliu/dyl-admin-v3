package com.diyiliu.web.grain;

import com.diyiliu.web.grain.dto.Stock;
import com.diyiliu.web.grain.facade.StockJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: StockController
 * Author: DIYILIU
 * Update: 2018-05-21 21:26
 */

@RestController
@RequestMapping("/grain")
public class StockController {

    @Resource
    private StockJpa stockJpa;

    @PostMapping("/stockList")
    public Map stockList(@RequestParam int pageNo, @RequestParam int pageSize,
                        @RequestParam(required = false) String search) {
        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "createTime")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Stock> stockPage = stockJpa.findAll(pageable);
        Map respMap = new HashMap();
        respMap.put("data", stockPage.getContent());
        respMap.put("total", stockPage.getTotalElements());

        return respMap;
    }
}
