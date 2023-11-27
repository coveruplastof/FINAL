package com.last.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.last.application.dto.RecommandDTO;
import com.last.application.dto.RecommandVO;
import com.last.application.dto.StringVO;
import com.last.application.dto.faceDTO;
import com.last.domain.entitiy.FaceEntitiy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class resttemplate {
    @Value("${file.dir}")
    private String fileDir;
    private final String url = "http://192.168.0.40:5000/submit";


    private zabaldaera memberService;

    @Autowired
    public resttemplate(zabaldaera memberService) {
        this.memberService = memberService;
    }


    private String getBase64String(MultipartFile multipartFile) throws Exception {
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
    @PostMapping("/posttester")
    public String requestToFlasker2(String fileName, HttpServletResponse responser, @RequestParam("file") MultipartFile files,faceDTO facedto) throws Exception {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000000);
        factory.setReadTimeout(10000000);
        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
        System.out.println(files.getOriginalFilename());
        RestTemplate restTemplate = new RestTemplate(factory);
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Body set
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        String savedPath = fileDir + files.getOriginalFilename();
        files.transferTo(new File(savedPath));
        Resource resource1 = new FileSystemResource(savedPath);

        body.add("file", resource1);
        System.out.println(savedPath);
        // Message
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
        // Request
        System.out.println(requestEntity);
        // Request
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] fileData = response.getBody();
            String responseBody = new String(fileData, StandardCharsets.UTF_8);
            System.out.println(responseBody);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            // Extract the value of "text_data2"
            String textData1 = jsonNode.get("shape").asText();
            String textData2 = jsonNode.get("tone").asText();
            System.out.println(textData1);

            System.out.println(textData2);
            faceDTO facedtao = new faceDTO();
            facedtao.setFace_id(3);
            facedtao.setPersonal_color(textData1);
            facedtao.setFace_shape(textData2);
            //int memberinsert = memberService.createMember(facedtao);
            int memberupdate = memberService.updateMember(facedtao);
            return "성공";


        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "null";
        }
    }
    @PostMapping("/recommand")
    public RedirectView requestTorecommand(String fileName, HttpServletResponse responser, byte[] byteArray,@RequestParam("txtzz") String txts) throws Exception {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000000);
        factory.setReadTimeout(10000000);
        factory.setBufferRequestBody(false); // 파일 전송은 이 설정을 꼭 해주자.
        RestTemplate restTemplate = new RestTemplate(factory);
        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        // Body set
        httpHeaders.set("Accept-Charset", "UTF-8");
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        System.out.println("Received text: " + txts);

        body.add("txta", txts);
        // Message
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);
        // Request
        System.out.println(requestEntity);
        // Request
        ResponseEntity<StringVO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, StringVO.class);
        System.out.println(response);

        if (response.getStatusCode() == HttpStatus.OK) {
            StringVO fileData = response.getBody();
            System.out.println(fileData);

//            System.out.println(responseBody);
            ObjectMapper objectMapper = new ObjectMapper();
            StringVO dtoArray = objectMapper.convertValue(fileData, StringVO.class);
            System.out.println(dtoArray);
            StringVO stringvo = new StringVO();
            stringvo.setEssence(dtoArray.getEssence());
            stringvo.setSkin(dtoArray.getSkin());
            stringvo.setLocation(dtoArray.getLocation());
            stringvo.setCrime(dtoArray.getCrime());
            stringvo.setSkin_id(1);
            System.out.println("hi"+stringvo);
            memberService.CreateRecommand(stringvo);


//            JsonNode jsonNode = objectMapper.readTree(responseBody);
//            // Extract the value of "text_data2"
//            String textData1 = jsonNode.get("essence").asText();
//            String textData2 = jsonNode.get("skin").asText();
//            String textData3 = jsonNode.get("crime").asText();
//            String textData4 = jsonNode.get("loation").asText();
//            System.out.println(textData1);
//            System.out.println(textData2);
//            System.out.println(textData3);
//            System.out.println(textData4);
//            RecommandDTO recommanddao = new RecommandDTO();
//            recommanddao.setEssence(textData1);
//            recommanddao.setSkin(textData2);
//            recommanddao.setCrime(textData3);
//            recommanddao.setLoation(textData4);
//            //int memberinsert = memberService.createMember(facedtao);
            return new RedirectView("aisearchresult");


        } else {
           System.out.println("Request failed with status code: " + response.getStatusCodeValue());
           return new RedirectView("aisearchresult");
       }
    }
}
