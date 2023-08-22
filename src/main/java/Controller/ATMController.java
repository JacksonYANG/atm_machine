package Controller;

import Service.AcctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ATMController {
    @Autowired
    private AcctService acctService;

    @GetMapping("/balance/{userName}")
    public double checkBalance(@PathVariable String userName){
        return 0;
    }
}
