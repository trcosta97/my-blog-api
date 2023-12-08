package com.myBlog.api.controller;

import com.myBlog.api.domain.Account;
import com.myBlog.api.dto.account.CreateAccountDTO;
import com.myBlog.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity create(@RequestBody CreateAccountDTO data, UriComponentsBuilder uri){
        var account = new Account(data);
        var uriAccount = uri.path("/account/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uriAccount).body(accountService.create(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Account>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(accountService.getAll(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Account data){
        return ResponseEntity.ok(accountService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        accountService.logicalDelete(id);
        return ResponseEntity.noContent().build();
    }
}
