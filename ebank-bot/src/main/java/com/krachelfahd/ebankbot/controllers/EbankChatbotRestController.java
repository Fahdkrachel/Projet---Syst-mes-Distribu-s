package com.krachelfahd.ebankbot.controllers;

import com.krachelfahd.ebankbot.agents.EbankAgent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankChatbotRestController {
    private EbankAgent ebankAgent;

    public EbankChatbotRestController(EbankAgent ebankAgent) {
        this.ebankAgent = ebankAgent;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "query",defaultValue = "Bonjour") String query){
        return ebankAgent.chat(query);
    }


}
