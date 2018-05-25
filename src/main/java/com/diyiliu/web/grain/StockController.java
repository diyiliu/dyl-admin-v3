package com.diyiliu.web.grain;

import com.diyiliu.support.util.DateUtil;
import com.diyiliu.web.grain.dto.Member;
import com.diyiliu.web.grain.dto.Sold;
import com.diyiliu.web.grain.dto.Stock;
import com.diyiliu.web.grain.facade.MemberJpa;
import com.diyiliu.web.grain.facade.SoldJpa;
import com.diyiliu.web.grain.facade.StockJpa;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

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

    @Resource
    private SoldJpa soldJpa;

    @PostMapping("/stockList")
    public Map stockList(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) String createTime, @RequestParam(required = false) String search) {

        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "createTime")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Stock> stockPage;
        if (StringUtils.isEmpty(search) && status == null && StringUtils.isEmpty(createTime)) {
            stockPage = stockJpa.findAll(pageable);
        } else {
            stockPage = stockJpa.findAll(
                    (Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                        Path<Integer> statusExp = root.get("status");
                        Path<String> serialNoExp = root.get("serialNo");
                        Path<Date> createTimeExp = root.get("createTime");
                        Path<Member> memberExp = root.get("member");

                        List<Predicate> list = new ArrayList();

                        if (StringUtils.isNotEmpty(search)) {
                            String like = "%" + search + "%";

                            Predicate predicate;
                            List<Member> members = memberJpa.findByNameLike("%" + search + "%");
                            if (CollectionUtils.isNotEmpty(members)) {
                                predicate = cb.or(new Predicate[]{cb.like(serialNoExp, like), memberExp.in(members)});
                            } else {
                                predicate = cb.like(serialNoExp, like);
                            }

                            list.add(predicate);
                        }

                        if (StringUtils.isNotEmpty(createTime)) {
                            list.add(betweenTime(createTime, createTimeExp, cb));
                        }

                        if (status != null) {
                            Predicate predicate = cb.equal(statusExp, status);
                            list.add(predicate);
                        }


                        Predicate[] predicates = list.toArray(new Predicate[]{});
                        return cb.and(predicates);
                    }, pageable);
        }

        Map respMap = new HashMap();
        respMap.put("data", stockPage.getContent());
        respMap.put("total", stockPage.getTotalElements());

        return respMap;
    }

    @PostMapping("/stock")
    public Integer saveStock(Stock stock) {
        Member member = stock.getMember();
        member = memberJpa.findByNameAndTel(member.getName(), member.getTel());
        if (member == null) {
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
        if (stock == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/stock")
    public Integer modifyStock(Stock stock) {
        Member member = stock.getMember();
        member = memberJpa.findByNameAndTel(member.getName(), member.getTel());
        if (member == null) {
            member = memberJpa.save(stock.getMember());
        }

        int suttle = stock.getGross() - stock.getTare();
        double money = suttle * stock.getPrice();

        Stock temp = stockJpa.findById(stock.getId());
        temp.setMember(member);
        temp.setGross(stock.getGross());
        temp.setTare(stock.getTare());
        temp.setPrice(stock.getPrice());
        temp.setSuttle(suttle);
        temp.setMoney(money);

        temp = stockJpa.save(temp);
        if (temp == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/stock")
    public Integer deleteStock(@RequestBody List<Long> ids) {
        stockJpa.deleteByIdIn(ids);

        return 1;
    }

    @PutMapping("/stockPay/{id}")
    public Integer stockPay(@PathVariable Long id) {
        Stock stock = stockJpa.findById(id);
        stock.setStatus(1);
        stock.setPayTime(new Date());
        stock = stockJpa.save(stock);
        if (stock == null) {

            return 0;
        }

        return 1;
    }


    @PostMapping("/soldList")
    public Map soldList(@RequestParam int pageNo, @RequestParam int pageSize,
                        @RequestParam(required = false) String createTime, @RequestParam(required = false) String search) {

        Sort sort = new Sort(new Sort.Order[]{new Sort.Order(Sort.Direction.DESC, "createTime")});
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, sort);

        Page<Sold> soldPage;
        if (StringUtils.isEmpty(search) && StringUtils.isEmpty(createTime)) {
            soldPage = soldJpa.findAll(pageable);
        } else {
            soldPage = soldJpa.findAll(
                    (Root<Sold> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
                        Path<String> serialNoExp = root.get("serialNo");
                        Path<String> companyExp = root.get("company");
                        Path<Date> createTimeExp = root.get("createTime");

                        List<Predicate> list = new ArrayList();

                        if (StringUtils.isNotEmpty(search)) {
                            String like = "%" + search + "%";
                            Predicate predicate = cb.or(new Predicate[]{cb.like(serialNoExp, like), cb.like(companyExp, like)});
                            list.add(predicate);
                        }

                        if (StringUtils.isNotEmpty(createTime)) {
                            list.add(betweenTime(createTime, createTimeExp, cb));
                        }

                        Predicate[] predicates = list.toArray(new Predicate[]{});
                        return cb.and(predicates);
                    }, pageable);
        }

        Map respMap = new HashMap();
        respMap.put("data", soldPage.getContent());
        respMap.put("total", soldPage.getTotalElements());

        return respMap;
    }

    @PostMapping("/sold")
    public Integer saveSold(Sold sold) {
        int suttle = sold.getGross() - sold.getTare();
        double money = suttle * sold.getPrice();
        sold.setSuttle(suttle);
        sold.setMoney(money);
        sold.setCreateTime(new Date());
        sold = soldJpa.save(sold);
        if (sold == null) {

            return 0;
        }

        return 1;
    }

    @PutMapping("/sold")
    public Integer modifySold(Sold sold) {
        int suttle = sold.getGross() - sold.getTare();
        double money = suttle * sold.getPrice();
        sold.setSuttle(suttle);
        sold.setMoney(money);

        // 填充创建时间
        Sold temp = soldJpa.findById(sold.getId());
        sold.setCreateTime(temp.getCreateTime());

        temp = soldJpa.save(sold);
        if (temp == null) {

            return 0;
        }

        return 1;
    }

    @Transactional
    @DeleteMapping("/sold")
    public Integer deleteSold(@RequestBody List<Long> ids) {
        soldJpa.deleteByIdIn(ids);

        return 1;
    }

    private Predicate betweenTime(String dateTime, Path<Date> datePath, CriteriaBuilder cb) {
        String starTime = dateTime.substring(0, 10);
        String endTime = dateTime.substring(13, 23);
        Date sTime = DateUtil.strToDate(starTime);
        Date eTime = DateUtil.strToDate(endTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(eTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        eTime = calendar.getTime();

        return cb.between(datePath, sTime, eTime);
    }
}
