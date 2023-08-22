package Repository;

import Model.TxnFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TxnFlowRepository extends JpaRepository<TxnFlow, Long> {

    //查询所有的交易流水
    List<TxnFlow> findByUserNameOrderByCreatedTime(String userName);


}
