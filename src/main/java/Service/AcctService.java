package Service;

import Model.Acct;
import Repository.AcctRepository;
import Repository.TxnFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AcctService {
    @Autowired
    private AcctRepository acctRepository;

    @Autowired
    private TxnFlowRepository txnFlowRepository;

    //通过用户名登陆检查余额
    public double checkBalanceWithUserName(String userName){
        Optional<Acct> optionalAcct = acctRepository.findByUserName(userName);
        return optionalAcct.map(Acct::getBalance).orElse(0.0);
    }

    //通过手机号登陆检查余额
    public double checkBalanceWithPhone(String phone){
        Optional<Acct> optionalAcct = acctRepository.findByPhone(phone);
        return optionalAcct.map(Acct::getBalance).orElse(0.0);
    }

    //存款
    public void deposit(String userName, double amount){
        Optional<Acct> optionAcct = acctRepository.findByUserName(userName);
        if(optionAcct.isPresent()){
            Acct real_acct = optionAcct.get();
            real_acct.setBalance(real_acct.getBalance()+amount);
            acctRepository.save(real_acct);
        }
    }

    //取款
    public boolean withDraw(String userName, double amount){
        Optional<Acct> optionalAcct = acctRepository.findByUserName(userName);
        if(optionalAcct.isPresent()){
            Acct acct = optionalAcct.get();
            if(acct.getBalance() - amount >=0){
                acct.setBalance(acct.getBalance() - amount);
                acctRepository.save(acct);
                return true;
            }
        }
        return false;
    }

    //增加交易记录


}
