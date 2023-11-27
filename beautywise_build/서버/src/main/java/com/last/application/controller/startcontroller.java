package com.last.application.controller;

import com.last.application.dto.RecommandDTO;
import com.last.application.dto.StringVO;
import com.last.application.dto.faceDTO;
import com.last.application.service.UserService;
import com.last.application.service.zabaldaera;
import com.last.domain.dto.UserDTO;
import com.last.domain.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Controller
public class startcontroller {


    private zabaldaera memberService;
    private UserService userService;
    @Autowired
    public startcontroller(zabaldaera memberService) {
        this.memberService = memberService;
    }

    @GetMapping("index")
    public ModelAndView index(ModelAndView model){
        System.out.println("index");
        model.setViewName("index");
        return model;
    }

    @GetMapping("recommand")
    public ModelAndView recommand(ModelAndView model){
        System.out.println("createRecommand");
        model.setViewName("productRecommand");
        return model;
    }
//    @GetMapping("login")
//    public ModelAndView login(ModelAndView model){
//        System.out.println("login");
//        model.setViewName("login");
//        return model;
//    }

    @GetMapping("aicommunity")
    public ModelAndView AIrecommand(ModelAndView model){
        System.out.println("잘나왔음");
        model.setViewName("aicommunity1");
        return model;
    }
    @GetMapping("community")
    public ModelAndView community(ModelAndView model){
        System.out.println("community");
        model.setViewName("community");
        return model;
    }
    @GetMapping("productRating")
    public ModelAndView productRating(ModelAndView model){
        System.out.println("productRating");
        model.setViewName("productRating");
        return model;
    }

    @GetMapping("productRecommand")
    public ModelAndView productRecommand(ModelAndView model){
        System.out.println("productRecommand");
        model.setViewName("productRecommand");
        return model;
    }

    @GetMapping("mypage")
    public ModelAndView mypage(ModelAndView model){
        System.out.println("mypage");
        faceDTO facedtao = new faceDTO();
        facedtao.setFace_id(3);
        List<faceDTO> memberList = memberService.selectFace(facedtao.getFace_id());
        model.addObject("personalcolor", memberList.get(0).getPersonal_color()); // 모델 데이터 추가
        model.setViewName("mypage");
        return model;
    }

    @GetMapping("flask")
    public ModelAndView flask(ModelAndView model){
        System.out.println("flask");
        model.setViewName("flask");
        return model;
    }

    @GetMapping("josa")
    public ModelAndView josa(ModelAndView model){
        System.out.println("josa");
        model.setViewName("josa");
        return model;
    }

    @GetMapping("josa1")
    public ModelAndView josa1(ModelAndView model){
        System.out.println("josa1");
        model.setViewName("aicommunity1");
        return model;
    }
    @GetMapping("survey")
    public ModelAndView mybatis(ModelAndView model,faceDTO facedto) {
        faceDTO facedtao = new faceDTO();
        facedtao.setFace_id(3);
        facedtao.setPersonal_color("hi");
        facedtao.setFace_shape("hia");
        List<faceDTO> memberList = memberService.selectFace(facedtao.getFace_id());
        model.addObject("personalcolor", memberList.get(0).getPersonal_color()); // 모델 데이터 추가
        model.addObject("faceshape", memberList.get(0).getFace_shape()); // 모델 데이터 추가
        model.setViewName("aicommunity"); // 뷰의 이름을 설정해야 합니다.

        return model; // ModelView 객체 반환
    }

    @GetMapping("aisearchresult")
    public ModelAndView aisearchresult1(ModelAndView model,faceDTO facedto) {
        StringVO facedtao = new StringVO();
        facedtao.setSkin_id(1);
        String directoryPath = System.getProperty("user.dir") + "/src/main/resources/static/img/";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        System.out.println(System.getProperty("user.dir"));
        System.out.println("hi"+files[0]);


        List<StringVO> memberList = memberService.SelectRecommand(facedtao.getSkin_id());
        model.addObject("aa", memberList.get(0).getEssence()); // 모델 데이터 추가
        model.addObject("bb", memberList.get(0).getLocation()); // 모델 데이터 추가
        model.addObject("cc", memberList.get(0).getCrime()); // 모델 데이터 추가
        model.addObject("dd", memberList.get(0).getSkin()); // 모델 데이터 추가

        model.setViewName("airesult12"); // 뷰의 이름을 설정해야 합니다.

        return model; // ModelView 객체 반환
    }

    @GetMapping("aisearch")
    public ModelAndView aisearch(ModelAndView model, RecommandDTO recommandDTO) {
        RecommandDTO recommanddto = new RecommandDTO();

        //List<RecommandDTO> memberList = memberService.SelectRecommand(1);
        //model.addObject("personalcolor", memberList); // 모델 데이터 추가

        model.setViewName("aisearch"); // 뷰의 이름을 설정해야 합니다.

        return model; // ModelView 객체 반환
    }
    @GetMapping("survey1")
    public ModelAndView survey1(ModelAndView model){
        System.out.println("survey1");
        model.setViewName("survey1");
        return model;
    }

    @GetMapping("/test")
    public String getTest(HttpServletRequest request, Model model) {
        String username = String.valueOf(request.getAttribute("username" ));
        return "test";
    }

    @PostMapping("/save-to-mysql")
    public ResponseEntity<String> saveToMysql(@Valid @RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null || userDTO.getProfileNickname() == null || userDTO.getProfileImage() == null
                    || userDTO.getAccountEmail() == null || userDTO.getAccessToken() == null) {

                // Add logging to identify which field is null
                System.out.println("userDTO: " + userDTO);
                assert userDTO != null;
                System.out.println("ProfileNickname: " + userDTO.getProfileNickname());
                System.out.println("ProfileImage: " + userDTO.getProfileImage());
                System.out.println("AccountEmail: " + userDTO.getAccountEmail());
                System.out.println("AccessToken: " + userDTO.getAccessToken());

                return ResponseEntity.badRequest().body("One or more required fields are missing");
            }

            // Additional validation if needed

            User user = new User(userDTO.getProfileNickname(), userDTO.getProfileImage(),
                    userDTO.getAccountEmail(), userDTO.getAccessToken());
            userService.saveUser(user);

            return ResponseEntity.ok("User saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save user");
        }
    }

    //로그아웃시 토큰 삭제
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("accessToken") String accessToken) {
        try {
            // Call the UserService to delete the user by access token
            userService.deleteUserByToken(accessToken);

            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to logout");
        }
    }

}
