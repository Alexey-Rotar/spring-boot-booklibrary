package ru.gb.springbootlesson3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.springbootlesson3.services.BookService;
import ru.gb.springbootlesson3.services.IssueService;
import ru.gb.springbootlesson3.services.ReaderService;

@Controller
@RequestMapping("ui")
public class UIController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private IssueService issueService;

    @GetMapping("books")
    public String getAllBooks(Model model){
        model.addAttribute("books", bookService.getBookList());
        return "books.html";
    }

    @GetMapping("readers")
    public String getAllReaders(Model model){
        model.addAttribute("readers", readerService.getReaderList());
        return "readers.html";
    }

    @GetMapping("issues")
    public String getAllIssues(Model model){
        model.addAttribute("issues", issueService.getIssueList());
        model.addAttribute("readerService", readerService);
        model.addAttribute("bookService", bookService);
        return "issues.html";
    }

    @GetMapping("reader/{id}")
    public String getReaderIssues(@PathVariable long id, Model model){
        model.addAttribute("reader", readerService.findReader(id));
        model.addAttribute("booksId", issueService.getNotReturnedBooksByReaderId(id));
        model.addAttribute("bookService", bookService);
        return "reader.html";
    }

}

