package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
            throw new InvalidMessageException("Message cannot be empty.");
        }

        Integer messageLen = newMessage.getMessageText().length();
        if (messageLen > 255) {
            throw new InvalidMessageException("Message exceeds 255 characters.");
        }

        if (!accountRepository.existsByAccountId(newMessage.getPostedBy())) {
            throw new InvalidPosterException();
        }

        return messageRepository.save(newMessage);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        boolean existMessage = messageRepository.existsByMessageId(messageId);
        if (existMessage) {
            return messageRepository.findByMessageId(messageId);
        }
        throw new InvalidMessageException();
    }

    public Integer deleteByMessageId(Integer messageId) {
        if (messageRepository.existsByMessageId(messageId)) {
            Message messageToBeDeleted = messageRepository.findByMessageId(messageId);
            messageRepository.delete(messageToBeDeleted);
            return 1;
        }
        return 0;
    }

    @Transactional
    public Integer updateMessageText(Integer messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.trim().isEmpty()) {
            throw new InvalidMessageException("Message cannot be empty.");
        }

        if (newMessageText.length() > 255) {
            throw new InvalidMessageException("Message exceeds 255 characters.");
        }

        if (!messageRepository.existsByMessageId(messageId)) {
            throw new InvalidMessageException("The messageId does not exist.");
        }

        int updatedRows = messageRepository.updateMessageText(messageId, newMessageText);
        return updatedRows; 
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
