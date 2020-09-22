package com.bruno.tinderclone.controller;

import com.bruno.tinderclone.exception.UserNotFoundException;
import com.bruno.tinderclone.model.Cards;
import com.bruno.tinderclone.model.Like;
import com.bruno.tinderclone.model.response.Response;
import com.bruno.tinderclone.model.response.ResponseList;
import com.bruno.tinderclone.service.CardService;
import com.bruno.tinderclone.utils.ConvertPageToCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/cards")
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<Response<Page<Cards>>> fetch(Pageable pageable) throws UserNotFoundException {
        Page<Cards> page = cardService.fetchCards(pageable);
        ResponseList<Cards> response = new ResponseList<>();

        List<Cards> listCards = ConvertPageToCard.convert(page);
        response.setData(listCards);
        response.addStatusCode(HttpStatus.CREATED.value());
        createSelfLink(pageable.getPageNumber(), pageable.getPageSize(), response);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/matches/{userId}")
    public ResponseEntity<Response<Cards>> fetchMatches(@PathVariable String userId) throws UserNotFoundException {
        List<Cards> matches = cardService.fetchMatches(userId);
        ResponseList response = new ResponseList();
        response.setData(matches);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Response<Cards>> fetchCard(@PathVariable String userId) throws UserNotFoundException{
        Cards card = cardService.fetchUser(userId);

        Response response = new Response();
        response.setData(card);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/liked/{userId}")
    public Cards likedPeople(@PathVariable String userId){
        return cardService.fetchLiked(userId);
    }

    @PostMapping(value = "/add/like")
    public ResponseEntity<Response<Cards>> like(@RequestBody Like like) throws UserNotFoundException {

        Cards card = cardService.addLike(like);
        Response response = new Response();
        response.setData(card);
        response.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping(value = "/remove/like")
    public void dislike(@RequestBody Like like){
        try {
            cardService.removeLike(like);
        } catch (UserNotFoundException e) {
            System.out.println("User not found");
        }
    }

    private void createSelfLink(int pageNumber, int pageSize, ResponseList response) {
        pageNumber = pageNumber + 1;
        Link selfLink = WebMvcLinkBuilder.linkTo(Cards.class).slash("cards?page=" + pageNumber + "&size=" + pageSize).withSelfRel();
        response.add(selfLink);
    }

}
