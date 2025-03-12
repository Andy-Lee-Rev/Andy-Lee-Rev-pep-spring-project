package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.exception.InvalidMessageException;
import com.example.exception.InvalidPosterException;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message newMessage) {
        if (newMessage.getMessageText() == null || newMessage.getMessageText().trim().isEmpty()) {
            throw new InvalidMessageException();
        }

        Integer messageLen = newMessage.getMessageText().length();
        if (messageLen > 255) {
            throw new InvalidMessageException();
        }

        if (!accountRepository.existsByAccountId(newMessage.getPostedBy())) {
            throw new InvalidPosterException();
        }

        return messageRepository.save(newMessage);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}
