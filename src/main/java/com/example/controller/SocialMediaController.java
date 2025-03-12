package com.example.controller;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid account details.");
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> login(@RequestBody Account account) {
        try {
            Account existsAccount = accountService.login(account);
            return ResponseEntity.status(HttpStatus.OK).body(existsAccount);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials.");
        }
    }

    // @RequestMapping(value = "/messages", method = RequestMethod.POST)
    // public @ResponseBody ResponseEntity<Message> postMessage(@RequestBody Message newMessage) {
    //     Message postedMessage = messageService.postMessage(newMessage);
    //     if (postedMessage != null) {
    //         return ResponseEntity.status(HttpStatus.OK).body(postedMessage);
    //     }
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(postedMessage);
    // }

    // @RequestMapping(value = "/messages", method = RequestMethod.GET)
    // public @ResponseBody ResponseEntity<List<Message>> getMessages() {
    //     List<Message> messageList = messageService.getMessages();
    //     return ResponseEntity.status(HttpStatus.OK).body(messageList);
    // }

    // @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    // public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable Long messageId) {
    //     Message message = messageService.getMessageById(messageId);
    //     return ResponseEntity.status(HttpStatus.OK).body(message);
    // }

    // @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.DELETE)
    // public @ResponseBody Message deleteMessageById(@PathVariable Long messageId) {
    //     return new ResponseEntity<>(messageService.deleteMessageById(messageId), HttpStatus.OK);
    // }

    // @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.PATCH)
    // public @ResponseBody ResponseEntity<Message> updateMessageById(@PathVariable Long messageId) {
    //     if (messageService.updateMessageById(messageId)) {
    //         return new ResponseEntity<>(messageService.updateMessageById(messageId), HttpStatus.OK);
    //     }
    //     return new ResponseEntity<>(messageService.updateMessageById(messageId), HttpStatus.BAD_REQUEST);
    // }

    // @RequestMapping(value = "/accounts/{accountId}/messages", method = RequestMethod.GET)
    // public @ResponseBody Message getMessagesByAccountId(@PathVariable Long accountId) {
    //     return new ResponseEntity<>( messageService.getMessagesByAccountId(accountId), HttpStatus.OK);
    // }
}
