package com.bruno.tinderclone.service;

import com.bruno.tinderclone.exception.UserNotFoundException;
import com.bruno.tinderclone.model.Cards;
import com.bruno.tinderclone.model.Like;
import com.bruno.tinderclone.repository.card.CardRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class CardServiceTest {

    @Autowired
    private CardService cardService;
    @MockBean
    private CardRepository cardRepository;


    @Test
    public void testSave(){
        Cards card = new Cards("Jimmy", "nourl", "led zeppelin guitarist");
        BDDMockito.given(cardRepository.save(Mockito.any(Cards.class))).willReturn(new Cards("Jimmy", "nourl", "led zeppelin guitarist"));

        //Mockito.when(cardRepository.save(card)).thenReturn(card);

        Cards savedCard = cardService.save();

        assertNotNull(savedCard);

        assertEquals(savedCard.getName(), card.getName());
        assertEquals(savedCard.getImage(), card.getImage());
        assertEquals(savedCard.getBio(), card.getBio());
    }

    @Test
    public void testFetchCards() {
        List<Cards> listCards = new ArrayList<>();
        listCards.add(new Cards("Jimmy", "nourl", "led zeppelin guitarist"));
        listCards.add(new Cards("Robert", "nourl", "led zeppelin singer"));
        Page<Cards> cards = new PageImpl<Cards>(listCards);
        BDDMockito.given(cardRepository.findAllByIdNot(Mockito.any(String.class), Mockito.any(Pageable.class))).willReturn(cards);

        assertEquals(cards.getContent().size(), 2);
    }

    @Test
    public void testAddLike() throws UserNotFoundException{
        Like like = new Like("1234");
        Cards card = new Cards("Jimmy", "nourl", "led zeppelin guitarist");
        Cards cardWithLike = card;
        cardWithLike.addLike(like);
        Optional<Cards> optional = Optional.of(card);
        BDDMockito.given(cardRepository.findById(Mockito.any(String.class))).willReturn(optional);
        BDDMockito.given(cardRepository.save(Mockito.any(Cards.class))).willReturn(cardWithLike);

        Cards response = cardService.addLike(like);

        assertEquals(response.getLikes().size(), 1);

    }

    @Test
    public void testAddLikeException() {
        Optional<Cards> optional = Optional.ofNullable(null);
        BDDMockito.given(cardRepository.findById(Mockito.any(String.class))).willReturn(optional);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            cardService.addLike(new Like("1234"));
        });

        assertTrue(exception.getMessage().equals("User not found"));
    }

    @Test
    public void testRemoveLike() throws UserNotFoundException {
        Like like = new Like("1234");
        Cards card = new Cards("Jimmy", "nourl", "led zeppelin guitarist");
        Cards cardWithLike = card;
        cardWithLike.addLike(like);
        Optional<Cards> optional = Optional.of(cardWithLike);
        BDDMockito.given(cardRepository.findById(Mockito.any(String.class))).willReturn(optional);
        BDDMockito.given(cardRepository.save(Mockito.any(Cards.class))).willReturn(card);

        Cards response = cardService.removeLike(like);

        assertEquals(response.getLikes().size(), 0);
    }

    @Test
    public void testRemoveLikeException() throws UserNotFoundException {
        Like like = new Like("1234");
        Cards card = new Cards("Jimmy", "nourl", "led zeppelin guitarist");
        Cards cardWithLike = card;
        cardWithLike.addLike(like);
        Optional<Cards> optional = Optional.ofNullable(null);
        BDDMockito.given(cardRepository.findById(Mockito.any(String.class))).willReturn(optional);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            cardService.removeLike(new Like("1234"));
        });

        assertTrue(exception.getMessage().equals("User not found"));
    }

}
