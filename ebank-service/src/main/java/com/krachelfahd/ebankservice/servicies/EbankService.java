package com.krachelfahd.ebankservice.servicies;

import com.krachelfahd.ebankservice.entities.BankAccount;
import com.krachelfahd.ebankservice.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbankService {

    private BankAccountRepository accountRepository;
    // injection via constructeur
    public EbankService(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }
    public BankAccount getBankAccountById(String id){
        return accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));
    }
    public BankAccount save(BankAccount bankAccount){
        return accountRepository.save(bankAccount);
    }

}
