package com.example.chatgptbot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.temporal.Temporal;

@Component
@RequiredArgsConstructor
public class BotInitializer {

        private final ChatGPTBot chatGPTBot;

        @SneakyThrows
        @EventListener({ContextRefreshedEvent.class})
        public void init() {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(chatGPTBot);
        }
}
