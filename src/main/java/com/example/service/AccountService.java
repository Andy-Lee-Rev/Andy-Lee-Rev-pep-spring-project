package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import com.example.entity.*;
import com.example.exception.*;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount) {
        if (accountRepository.existsByUsername(newAccount.getUsername())) {
            throw new DuplicateUsernameException();
        }

        if (newAccount.getUsername() == null || newAccount.getPassword().length() < 4) {
            throw new InvalidCredentialsException();
        }

        return accountRepository.save(newAccount);
    }

    public Account login(Account account) {
        Boolean existsAccount = accountRepository.existsByUsername(account.getUsername());
        if (existsAccount) {
            Account foundAccount = accountRepository.findByUsername(account.getUsername());
            if (foundAccount.getPassword().equals(account.getPassword())) {
                return foundAccount;
            }
        }
        throw new InvalidCredentialsException();
    }
}
