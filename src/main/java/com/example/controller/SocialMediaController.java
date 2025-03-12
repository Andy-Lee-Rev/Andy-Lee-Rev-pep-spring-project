package com.example.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import com.example.entity.*;
import com.example.service.*;
import com.example.exception.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> register(@RequestBody Account newAccount) {
        try {
            Account registeredAccount = accountService.register(newAccount);
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> login(@RequestBody Account account) {
        try {
            Account existsAccount = accountService.login(account);
            return ResponseEntity.status(HttpStatus.OK).body(existsAccount);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> postMessage(@RequestBody Message newMessage) {
        try {
            Message postedMessage = messageService.postMessage(newMessage);
            return ResponseEntity.status(HttpStatus.OK).body(postedMessage);
        } catch (InvalidMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidPosterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Message>> getMessages() {
        List<Message> messageList = messageService.getMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (InvalidMessageException e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    // Always OK, return 1 or null upon deletion or no deletion.
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Integer> deleteByMessageId(@PathVariable Integer messageId) {
        Integer result = messageService.deleteByMessageId(messageId);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.PATCH)
    public @ResponseBody ResponseEntity<?> updateMessageById(@PathVariable Integer messageId, @RequestBody Map<String, String> requestBody) {
        String newMessageText = requestBody.get("messageText");
        try {
            Integer updatedRows = messageService.updateMessageText(messageId, newMessageText);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRows);
        } catch (InvalidMessageException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }

    @RequestMapping(value = "/accounts/{accountId}/messages", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}
