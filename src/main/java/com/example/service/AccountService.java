package com.example.service;

import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.*;
import com.example.exception.*;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public Account register(Account newAccount) {
        if (accountRepository.existsByUsername(newAccount.getUsername())) {
            throw new DuplicateUsernameException();
        }

        if (newAccount.getUsername() == null || newAccount.getPassword().length() < 4) {
            throw new InvalidCredentialsException();
        }

        return accountRepository.save(newAccount);
    }
}
