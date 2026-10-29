package com.krachelfahd.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class EbankAgent {
    private ChatClient chatClient;

    public EbankAgent(ChatClient.Builder chatClient, ChatMemory chatMemory, ToolCallbackProvider tools) {
        this.chatClient = chatClient
                .defaultSystem("""
                         Vous un assistant qui se charge de répondre aux question
                         de l'utilisateur en fonction du contexte fourni a propo des cliennts et des compte banciare.
                         Si aucun contexte n'est fourni, répond avec JE NE SAIS PAS
                         """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
    }


    public String chat( String query){
        return chatClient.prompt(query).call().content();
    }


}
