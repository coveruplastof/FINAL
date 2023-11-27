package com.last.application.service;

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

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class hi4 {
    @Value("${file.dir}")
    private String fileDir;
    private final String url = "http://192.168.0.80:5000/submit";

    private String getBase64String(MultipartFile multipartFile) throws Exception {
        byte[] bytes = multipartFile.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
    @PostMapping("/hanbinim")
    public String requestToFlasker2(String fileName, HttpServletResponse responser, @RequestParam("file") MultipartFile files) throws Exception {
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
            responseBody.toString();
            return responseBody;

        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
            return "null";
        }
    }
}
