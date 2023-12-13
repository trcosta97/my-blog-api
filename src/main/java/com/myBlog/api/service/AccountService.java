package com.myBlog.api.service;

import com.myBlog.api.domain.account.Account;
import com.myBlog.api.exception.EntityNotFoundException;
import com.myBlog.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;



    public Account create(Account account) {

        return accountRepository.save(account);
    }

    public Account getById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        throw new EntityNotFoundException("Account not found");
    }

    public Page<Account> getAll(Integer page, Integer size) {
        return accountRepository.findAll(PageRequest.of(page, size));
    }

    public Account update(Long id, Account data) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account updatedAccount = optionalAccount.get();
            if(data.getName() != null)
                updatedAccount.setName(data.getName());
            if(data.getLastName() != null)
                updatedAccount.setLastName(data.getLastName());
            if(data.getLogin() != null)
                updatedAccount.setLogin(data.getLogin());
            if(data.getPassword() != null)
                updatedAccount.setPassword(data.getPassword());
            updatedAccount.setLastUpdate(LocalDateTime.now());
            return accountRepository.save(updatedAccount);
        }
        throw new EntityNotFoundException("Account not found");
    }

    public void logicalDelete(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account deletedAccount = optionalAccount.get();
            deletedAccount.setActive(false);
            accountRepository.save(deletedAccount);
        }
        throw new EntityNotFoundException("Account not found");
    }

    public void delete(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.delete(optionalAccount.get());
        }
        throw new EntityNotFoundException("Account not found");
    }
}
