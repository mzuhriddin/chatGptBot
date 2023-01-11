package com.example.chatgptbot.service;

import com.example.chatgptbot.entity.User;
import com.example.chatgptbot.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public record BotService(UserRepository userRepository) {

    public SendMessage start(Update update, long chatId, String name) {
        User build = User.builder()
                .chatId(chatId + "")
                .fullName(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName())
                .build();
        Optional<User> optionalUser = userRepository.findByChatId(chatId + "");
        if (optionalUser.isEmpty()) {
            userRepository.save(build);
        }
        return SendMessage.builder()
                .text("Xush kelibsiz, " + name + "! \nBotimiz sizga GPT-3 tehnologiyasi orqali javob beradi.")
                .chatId(chatId)
                .build();
    }



    public SendMessage sendMessage(long chatId, String text) {
        return SendMessage.builder()
                .text(text)
                .chatId(chatId)
                .build();
    }
}
