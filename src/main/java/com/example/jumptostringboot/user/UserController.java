package com.example.jumptostringboot.user;

import com.example.jumptostringboot.question.Question;
import com.example.jumptostringboot.question.QuestionForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.print.PrinterGraphics;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워가 일치하지 않습니다.");
            return "signup_form";
        }

        try {
            userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(),
                    userCreateForm.getEmail(), userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
//회원정보 상세보기
        @RequestMapping(value = "/profile")
        public String profile(Model model, Principal principal) {
            SiteUser siteUser = this.userService.getUser(principal.getName());
            model.addAttribute("siteUser", siteUser);
            return "profile_detail";
        }
//회원정보 모달창
    @RequestMapping("/modal")
    public String modal(Model model, Principal principal){
        SiteUser siteUser = this.userService.getUser(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "profile_modal";
    }
//회원정보 수정
//        @PreAuthorize("isAuthenticated()")
//        @GetMapping("/modify")
//        public String modify(UserCreateForm userCreateForm, Principal principal){
//        Question question = this.questionService.getQuestion(id);
//            return "profile_form";
//        }
//        @PreAuthorize("isAuthenticated()")
//        @PostMapping("/modify")
//        public String profile_modify(@Valid UserCreateForm userCreateForm, Principal principal,
//                                     MultipartFile file) {
//        this.userService.modify();
//        }






}
