package ru.gb.springbootlesson3.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springbootlesson3.entity.Issue;
import ru.gb.springbootlesson3.services.IssueService;

import javax.naming.NoPermissionException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
//@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {

    @Autowired
    private IssueService service;


    /*
        GET - получение записей
        POST - создание записей (передаются данные о создаваемом объекте)
        PUT - изменение записей
        DELETE - запрос на удаление ресурса
     */

    @PostMapping("issue")
    // ResponseEntity<Issue> описывает ответ, напр. вернёт код ошибки, если не удалось найти запрашиваемый ресурс
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest issueRequest) {
        log.info("Поступил запрос на выдачу: readerId={}, bookId={}"
                , issueRequest.getReaderId(), issueRequest.getBookId());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createIssue(issueRequest)); // "Created" = код 201
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (NoPermissionException e){
            return ResponseEntity.status(409).build();
        }
    }

    // GET /issue/{id} - получить описание факта выдачи
    @GetMapping("issue/{id}")
    public ResponseEntity<Issue> getById(@PathVariable long id){
        log.info("Поступил запрос на выдачу: issueId={}", id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findIssue(id)); // "OK" = код 200
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /issue/{issueId} - закрывает факт выдачи
    @PutMapping("issue/{issueId}")
    public ResponseEntity<Issue> closeIssue(@PathVariable long issueId){
        log.info("Поступил запрос на закрытие выдачи: issueId={}", issueId);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.closeIssueById(issueId));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
