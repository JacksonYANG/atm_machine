package com.jacksonyang.atm_machine.Repository;

import com.jacksonyang.atm_machine.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//增删改查账户数据
public interface UserRepository extends JpaRepository<User, Long> {
    //用户名查询
    Optional<User> findByUserName(String userName);

    //手机号查询
    Optional<User> findByPhone(String phone);



}
