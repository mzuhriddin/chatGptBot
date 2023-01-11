package com.example.chatgptbot;

import com.example.chatgptbot.bot.ChatGPTBot;
import com.example.chatgptbot.service.BotService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableEncryptableProperties
public class ChatGptBotApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ChatGptBotApplication.class, args);
    }

}
