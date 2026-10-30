package com.krachelfahd.ebankbot.controllers;

import com.krachelfahd.ebankbot.agents.EbankAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;

@RestController
public class EbankChatbotRestController {
    private final EbankAgent ebankAgent;

    public EbankChatbotRestController(EbankAgent ebankAgent) {
        this.ebankAgent = ebankAgent;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return ebankAgent.chat(query, conversationId);
    }

    @GetMapping(value = "/chatStream", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatStream(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {
        return ebankAgent.chatStream(query, conversationId);
    }


}
