package com.krachelfahd.ebankservice.controllers;

import com.krachelfahd.ebankservice.entities.BankAccount;
import com.krachelfahd.ebankservice.servicies.EbankService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EbankRestController {
    private EbankService ebankService;
    public EbankRestController(EbankService ebankService) {
        this.ebankService = ebankService;
    }
    public List<BankAccount> getAllBankAccounts(){
        return ebankService.getAllBankAccounts();
    }
    public BankAccount getBankAccountById(String id){
        return ebankService.getBankAccountById(id);
    }
    public BankAccount save(@RequestBody BankAccount bankAccount){

        return ebankService.save(bankAccount);
    }

}
