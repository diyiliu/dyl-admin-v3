package com.diyiliu.web.pet;

import com.diyiliu.support.util.JacksonUtil;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: PetController
 * Author: DIYILIU
 * Update: 2018-07-18 21:51
 */

@RestController
@RequestMapping("/pet")
public class PetController {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Environment environment;

    @PostMapping("/list")
    public Map petList(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam(required = false) String search) {
        String url = environment.getProperty("pet.server-path") + "/pet/petList";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("page", pageNo + "");
        paramMap.add("size", pageSize + "");
        paramMap.add("search", search);

        HttpEntity<String> requestEntity = new HttpEntity(paramMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        Map respMap = new HashMap();
        try {
            respMap = JacksonUtil.toObject(responseEntity.getBody(), HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respMap;
    }

    @PostMapping("/track")
    public String petTrack(@RequestParam String search, @RequestParam String dateTime) {
        String url = environment.getProperty("pet.server-path") + "/pet/track";

        url += "?search=" + search + "&dateTime=" + dateTime;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        return responseEntity.getBody();
    }
}
