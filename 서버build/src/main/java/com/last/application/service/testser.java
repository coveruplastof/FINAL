//package com.last.application.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.last.application.dto.RecommandDTO;
//import com.last.application.dto.RecommandVO;
//import com.last.application.dto.StringVO;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//public class testser {
//    private zabaldaera memberService;
//
//    @Autowired
//    public testser(zabaldaera memberService) {
//        this.memberService = memberService;
//    }
//    private final String url = "http://127.0.0.1:5000/submit";
//
//
//    @PostMapping("/recommand")
//    public String requestTorecommand(String fileName, HttpServletResponse responser, byte[] byteArray, @RequestParam("txtzz") String txts) throws Exception {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(10000000);
//        factory.setReadTimeout(10000000);
//        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
//        RestTemplate restTemplate = new RestTemplate(factory);
//        // Header set
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
//        // Body set
//        httpHeaders.set("Accept-Charset", "UTF-8");
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        System.out.println("Received text: " + txts);
//
//        body.add("txta", txts);
//        // Message
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
//        // Request
//        System.out.println(requestEntity);
//        // Request
//        ResponseEntity<RecommandVO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, RecommandVO.class);
//        System.out.println(response);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            RecommandVO fileData = response.getBody();
//            System.out.println(fileData);
//
////            System.out.println(responseBody);
//            ObjectMapper objectMapper = new ObjectMapper();
//            RecommandVO dtoArray = objectMapper.convertValue(fileData, RecommandVO.class);
//            System.out.println(dtoArray);
//            RecommandDTO recommanddao = new RecommandDTO();
//            RecommandVO recommandvo = new RecommandVO();
//            StringVO stringvo = new StringVO();
//
//            recommandvo.setEssence(dtoArray.getEssence());
//            recommandvo.setSkin(dtoArray.getSkin());
//            recommandvo.setCrime(dtoArray.getCrime());
//            recommandvo.setLocation(dtoArray.getLocation());
//
//            for (String list: recommandvo.getEssence()){
//                System.out.println(recommandvo.getEssence().get(0));
//            }
//
//
//            System.out.println("hi"+ stringvo);
//            memberService.CreateRecommand(recommanddao);
//
//            return "성공";
//
//
//        } else {
//            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
//            return "null";
//        }
//    }}
