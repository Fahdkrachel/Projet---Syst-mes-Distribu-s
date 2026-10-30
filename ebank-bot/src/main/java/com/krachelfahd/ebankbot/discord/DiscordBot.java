package com.krachelfahd.ebankbot.discord;

import com.krachelfahd.ebankbot.agents.EbankAgent;
import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;



@DiscordController
public class DiscordBot {
    private static final int MAX_LENGTH = 1900;

    private final EbankAgent ebankAgent;

    public DiscordBot(EbankAgent ebankAgent) {
        this.ebankAgent = ebankAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        boolean isPrivate = !event.isFromGuild();
        boolean isMentioned = event.getMessage().getMentions()
                .isMentioned(event.getJDA().getSelfUser());


        if (!isPrivate && !isMentioned) {
            return;
        }

        String query = event.getMessage().getContentRaw()
                .replaceAll("<@!?\\d+>", "")
                .trim();
        if (query.isEmpty()) {
            return;
        }

        String conversationId = event.getChannel().getId() + "-" + event.getAuthor().getId();
        String response = ebankAgent.chat(query, conversationId);

        for (int i = 0; i < response.length(); i += MAX_LENGTH) {
            String part = response.substring(i, Math.min(response.length(), i + MAX_LENGTH));
            event.getChannel().sendMessage(part).queue();
        }
    }

}
