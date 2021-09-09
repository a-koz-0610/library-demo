package org.wecancodeit.librarydemo.rest.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.wecancodeit.librarydemo.models.Book;
import org.wecancodeit.librarydemo.models.HashTag;
import org.wecancodeit.librarydemo.repositories.BookRepository;
import org.wecancodeit.librarydemo.repositories.HashTagRepository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;

@RestController
@CrossOrigin
public class HashTagRestController {

    @Resource
    private HashTagRepository hashTagRepo;

    @Resource
    private BookRepository bookRepo;

    @GetMapping("/api/hashtags")
    public Collection<HashTag> getHashTags() {
        return (Collection<HashTag>) hashTagRepo.findAll();
    }

    @PostMapping("/api/add-hashtag")
    public Collection<HashTag> addHashTag(@RequestBody String body) throws JSONException {
        JSONObject newHashTag = new JSONObject(body);
        String hashTagName = newHashTag.getString("hashTagName");
        Optional<HashTag> hashTagToAddOpt = hashTagRepo.findByName(hashTagName);
        //add hashtag if not already in the database
        if (hashTagToAddOpt.isEmpty()) {
            HashTag hashTagToAdd = new HashTag(hashTagName);
            hashTagRepo.save(hashTagToAdd);
        }
        return (Collection<HashTag>) hashTagRepo.findAll();
    }

    @DeleteMapping("/api/hashtags/{id}/delete-hashtag")
    public Collection<HashTag> deleteHashTag(@PathVariable Long id) throws JSONException {
        Optional<HashTag> hashTagToRemoveOpt = hashTagRepo.findById(id);
        if(hashTagToRemoveOpt.isPresent()){
            for(Book book: hashTagToRemoveOpt.get().getBooks()){
                book.deleteHashTag(hashTagToRemoveOpt.get());
                bookRepo.save(book);
            }
            hashTagRepo.delete(hashTagToRemoveOpt.get());
        }
        return (Collection<HashTag>) hashTagRepo.findAll();
    }

}
