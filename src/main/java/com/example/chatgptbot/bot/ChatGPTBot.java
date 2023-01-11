package com.example.chatgptbot.bot;

import com.example.chatgptbot.service.BotService;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatGPTBot extends TelegramLongPollingBot {


    private final BotService botService;
    @Value("${open_ai_token}")
    private String openAiToken;
    @Value("${telegram_bot_username}")
    private String botUsername;

    @Value("${telegram_bot_token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        updates.forEach(update -> new Thread(() -> onUpdateReceived(update)).start());
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();
            String text = message.getText();
            if (text.equals("/start")) {
                execute(botService.start(update, chatId, message.getFrom().getFirstName()));
            } else {
                OpenAiService service = new OpenAiService(openAiToken);
                CompletionRequest completionRequest = CompletionRequest.builder()
                        .prompt(text)
                        .model("text-davinci-003")
                        .maxTokens(4000)
                        .temperature(0.9)
                        .n(1)
                        .bestOf(1)
                        .user(chatId + "")
                        .build();
                try {
                    CompletionResult completion = service.createCompletion(completionRequest);
                    execute(botService.sendMessage(chatId, completion.getChoices().get(0).getText()));
                }
                catch (Exception e) {
                    execute(botService.sendMessage(chatId, "Sorry for the inconvenience, internet connection is not stable. Please try again."));
                }


            }
        }


    }
}
