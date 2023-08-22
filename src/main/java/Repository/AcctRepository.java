package Repository;

import Model.Acct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//增删改查账户数据
public interface AcctRepository extends JpaRepository<Acct, Long> {
    //用户名查询
    Optional<Acct> findByUserName(String userName);

    //手机号查询
    Optional<Acct> findByPhone(String phone);



}
