package com.krachelfahd.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EbankAgent {
    private final ChatClient chatClient;

    public EbankAgent(ChatClient.Builder chatClientBuilder,
                      ChatMemory chatMemory,
                      ToolCallbackProvider tools) {

        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        Vous êtes un assistant qui répond aux questions
                        concernant les clients et les comptes bancaires,
                        en fonction du contexte fourni.
                        Si aucun contexte n'est fourni,
                        répondez par JE NE SAIS PAS.
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultTools(tools)
                .build();
    }


    public String chat(String query, String conversationId) {
        return chatClient.prompt()
                .user(query)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();
    }


    public Flux<String> chatStream(String query, String conversationId) {
        return chatClient.prompt()
                .user(query)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .stream()
                .content();
    }


}
