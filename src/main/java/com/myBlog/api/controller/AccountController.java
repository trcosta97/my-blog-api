package com.myBlog.api.controller;

import com.myBlog.api.domain.account.Account;
import com.myBlog.api.dto.account.CreateAccountDTO;
import com.myBlog.api.dto.account.ShowAccountDTO;
import com.myBlog.api.dto.account.UpdateAccountDTO;
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
        var showAccount = new ShowAccountDTO(account);
        var uriAccount = uri.path("/account/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(uriAccount).body(accountService.create(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        var showAccount = new ShowAccountDTO(accountService.getById(id));
        return ResponseEntity.ok(showAccount);
    }

    @GetMapping
    public ResponseEntity<Page<ShowAccountDTO>> getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size){
        var paginacao = accountService.getAll(page, size).map(ShowAccountDTO::new);
        return ResponseEntity.ok(paginacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateAccountDTO data){
        var updatedAccount = new ShowAccountDTO(accountService.update(id, new Account(data)));
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        accountService.logicalDelete(id);
        return ResponseEntity.noContent().build();
    }
}
