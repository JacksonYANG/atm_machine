package com.jacksonyang.atm_machine.Controller;

import com.jacksonyang.atm_machine.Model.User;
import com.jacksonyang.atm_machine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ATMController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //根目录重定向
    @GetMapping("/")
    public String redirect(){
        return "login";
    }

    //获取余额
    @GetMapping("/getBalance")
    public ResponseEntity<Map<String, Double>> checkBalance(@RequestParam String userName){
        Double balance = userService.checkBalanceWithUserName(userName);
        Map<String, Double> response = new HashMap<>();
        response.put("balance", balance);
        return ResponseEntity.ok(response);
    }

    //展示注册页面
    @GetMapping("/register")
    public String showRegister(){
        return "register";
    }

    //完成注册
    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, Object> requestData){
        userService.register((String) requestData.get("userName"), (String) requestData.get("password"), (String) requestData.get("phone"), (String) requestData.get("name"));
        ResponseEntity.ok("注册成功！");
        return "redirect:/login";
    }

    //登陆
    @PostMapping("/login")
    public String loginUser(@RequestBody Map<String, Object> requestData){
        UserDetails user = userService.loadUserByUsername((String) requestData.get("userName"));
        if (user != null && passwordEncoder.matches((String) requestData.get("password"), user.getPassword())) {
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    //存款
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody Map<String, Object> requestData){
        String username = (String) requestData.get("userName");
        double amount = (double) requestData.get("amount");
        userService.deposit(username, amount);
        return ResponseEntity.ok("存款成功");
    }

    //取款
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody Map<String, Object> requestData){
        String username = (String) requestData.get("userName");
        double amount = (double) requestData.get("amount");
        if (userService.withDraw(username, amount)){
            return ResponseEntity.ok("取款成功");
        } else return ResponseEntity.badRequest().body("余额不足，取款失败！");
    }
}
