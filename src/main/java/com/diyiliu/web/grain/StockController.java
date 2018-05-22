package com.diyiliu.web.grain;

import com.diyiliu.web.grain.dto.Member;
import com.diyiliu.web.grain.dto.Stock;
import com.diyiliu.web.grain.facade.MemberJpa;
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
import java.util.Date;
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

    @Resource
    private MemberJpa memberJpa;

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

    @PostMapping("/stock")
    public Integer saveStock(Stock stock){
        Member member = stock.getMember();
        member = memberJpa.findByNameAndTel(member.getName(), member.getTel());
        if (member == null){
            member = memberJpa.save(stock.getMember());
        }
        stock.setMember(member);

        int suttle = stock.getGross() - stock.getTare();
        double money = suttle * stock.getPrice();
        stock.setSuttle(suttle);
        stock.setMoney(money);
        stock.setCreateTime(new Date());
        stock.setStatus(0);
        stock = stockJpa.save(stock);
        if (stock == null){

            return 0;
        }

        return 1;
    }
}
