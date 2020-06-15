package org.wecancodeit.librarydemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wecancodeit.librarydemo.models.Book;
import org.wecancodeit.librarydemo.models.HashTag;
import org.wecancodeit.librarydemo.repositories.BookRepository;
import org.wecancodeit.librarydemo.repositories.HashTagRepository;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class BookController {

    @Resource
    private BookRepository bookRepo;
    @Resource
    private HashTagRepository hashTagRepo;

    @RequestMapping("/books")
    public String displayBooks(Model model){
        model.addAttribute("books", bookRepo.findAll());
        return "booksView";
    }

    @RequestMapping("/books/{id}")
    public String displaySingleBook(@PathVariable long id, Model model){
        Optional<Book> retrievedBook = bookRepo.findById(id);
        Book foundBook = retrievedBook.get();
        model.addAttribute("book", foundBook);
        return "bookView";
    }

    @PostMapping("/books/{id}/add-hashtag")
    public String addHashTagToBook(@RequestParam String hashTagName, @PathVariable long id){
        HashTag hashTagToAddToBook;
        Optional<HashTag> hashTagToAddOpt = hashTagRepo.findByName(hashTagName);
        if(hashTagToAddOpt.isEmpty()){
            hashTagToAddToBook = new HashTag(hashTagName);
            hashTagRepo.save(hashTagToAddToBook);
        } else {
            hashTagToAddToBook = hashTagToAddOpt.get();
        }
        Optional<Book> retrievedBook = bookRepo.findById(id);
        Book foundBook = retrievedBook.get();
        foundBook.addHashTag(hashTagToAddToBook);
        bookRepo.save(foundBook);
        return "redirect:/books/" + id;
    }

    @PostMapping("/books/{id}/delete-hashtag/{hashTagId}")
    public String deleteHashTag(@PathVariable Long id, @PathVariable Long hashTagId){
        Optional<HashTag> hashTagToRemoveOpt = hashTagRepo.findById(hashTagId);
        HashTag hashTagToRemove = hashTagToRemoveOpt.get();

        for(Book book: hashTagToRemove.getBooks()){
            book.deleteHashTag(hashTagToRemove);
            bookRepo.save(book);
        }

        Optional<Book> bookToRemoveHashTagFromOpt = bookRepo.findById(id);
        Book bookToRemoveHashTagFrom = bookToRemoveHashTagFromOpt.get();
        bookToRemoveHashTagFrom.deleteHashTag(hashTagToRemove);
        bookRepo.save(bookToRemoveHashTagFrom);
        hashTagRepo.delete(hashTagToRemove);
        return "redirect:/books/" + id;
    }
}
