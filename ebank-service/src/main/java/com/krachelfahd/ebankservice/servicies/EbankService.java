package com.krachelfahd.ebankservice.servicies;

import com.krachelfahd.ebankservice.entities.BankAccount;
import com.krachelfahd.ebankservice.feign.CustomerRestClient;
import com.krachelfahd.ebankservice.model.Customer;
import com.krachelfahd.ebankservice.repository.BankAccountRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EbankService {

    private BankAccountRepository accountRepository;
    private CustomerRestClient customerRestClient;
    // injection via constructeur les deux
    public EbankService(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }
    @McpTool(description ="Get All accounts")
    public List<BankAccount> getAllBankAccounts(){
        return accountRepository.findAll();
    }
    @McpTool(description = "Find a account by id")
    public BankAccount getBankAccountById(@McpToolParam(description = "The account id")String id){
        BankAccount bankAccount = accountRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Account not found"));
        bankAccount.setCustomer(
                customerRestClient.findCustomerById(bankAccount.getCustomerId())
                );
        return bankAccount;
    }
    @McpTool(description = "Save a new bank Account")
    public BankAccount save(@McpToolParam(description = "The bank account to save (balance,type,customerId)")
                                BankAccount bankAccount){
        try {
             Customer customer = customerRestClient.findCustomerById(bankAccount.getCustomerId());
             bankAccount.setId(UUID.randomUUID().toString());
             bankAccount.setCreatedAt(new Date());
             return accountRepository.save(bankAccount);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }


    }

}
