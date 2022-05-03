package com.example.library.controller;

import com.example.library.model.GeneralEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GeneralController<T extends GeneralEntity> {
    @PostMapping
    ResponseEntity<T> save(@RequestBody T entity);

    @PostMapping("/{uid}")
    ResponseEntity<T> update(@PathVariable("uid") String uid, @RequestBody T entity);

    @GetMapping
    ResponseEntity<List<T>> getAll(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "50", required = false) int size);

    @GetMapping("/{uid}")
    ResponseEntity<T> get(@PathVariable("uid") String uid);

    @DeleteMapping("/{uid}")
    ResponseEntity<Void> delete(@PathVariable("uid") String uid);
}
