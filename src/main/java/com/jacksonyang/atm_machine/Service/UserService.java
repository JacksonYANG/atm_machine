package com.jacksonyang.atm_machine.Service;

import com.jacksonyang.atm_machine.Model.User;
import com.jacksonyang.atm_machine.Model.TxnFlow;
import com.jacksonyang.atm_machine.Repository.UserRepository;
import com.jacksonyang.atm_machine.Repository.TxnFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TxnFlowRepository txnFlowRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    //注册服务
    public void register(String userName, String password, String phone, String name){
        User user = new User();
        user.setUserName(userName);
        String encryptPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encryptPassword);
        user.setPhone(phone);
        user.setName(name);
        userRepository.save(user);
    }

    //通过用户名登陆检查余额
    public double checkBalanceWithUserName(String userName){
        Optional<User> optionalAcct = userRepository.findByUserName(userName);
        return optionalAcct.map(User::getBalance).orElse(0.0);
    }

    //通过手机号登陆检查余额
    public double checkBalanceWithPhone(String phone){
        Optional<User> optionalAcct = userRepository.findByPhone(phone);
        return optionalAcct.map(User::getBalance).orElse(0.0);
    }

    //存款
    public void deposit(String userName, double amount){
        Optional<User> optionAcct = userRepository.findByUserName(userName);
        if(optionAcct.isPresent()){
            User real_user = optionAcct.get();
            real_user.setBalance(real_user.getBalance()+amount);
            userRepository.save(real_user);
            addTxnFLow(userName, amount, "存款");
        }
    }

    //取款
    public boolean withDraw(String userName, double amount){
        Optional<User> optionalAcct = userRepository.findByUserName(userName);
        if(optionalAcct.isPresent()){
            User user = optionalAcct.get();
            if(user.getBalance() - amount >=0){
                user.setBalance(user.getBalance() - amount);
                userRepository.save(user);
                addTxnFLow(userName, amount, "取款");
                return true;
            }
        }
        return false;
    }

    //增加交易记录
    private void addTxnFLow(String userName, double amount, String type){
        TxnFlow txnFlow = new TxnFlow();
        txnFlow.setUserName(userName);
        txnFlow.setTxnAmount(amount);
        txnFlow.setTxnType(type);
        txnFlowRepository.save(txnFlow);
    }


}
