package com.bruno.tinderclone.service;

import com.bruno.tinderclone.exception.UserNotFoundException;
import com.bruno.tinderclone.model.Cards;
import com.bruno.tinderclone.model.Like;
import com.bruno.tinderclone.repository.card.CardRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final String USER_ID = "5f6430d2bc8037104080a7b3";
    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    public Page<Cards> fetchCards(Pageable pageable) throws UserNotFoundException {
        Cards userCard = fetchUser(USER_ID);

        List<String> filteredIds = new ArrayList<>();
        filteredIds.add(userCard.getId());
        userCard.getLikes().forEach(item -> filteredIds.add(item));

        Page<Cards> cards = cardRepository.fetchAvailableCards(filteredIds, pageable);
        //Page<Cards> cards = cardRepository.findAllByIdNot(USER_ID, pageable);
        return cards;
    }

    public Cards fetchUser(String userId) throws UserNotFoundException {
        Optional<Cards> card = cardRepository.findById(userId);

        if(card.isPresent()){
            return card.get();
        }
        throw new UserNotFoundException("User not found");
    }

    public Cards fetchLiked(String userId){
        userId = USER_ID;
        ObjectId id = new ObjectId(userId);
        System.out.println(id);
        return cardRepository.findAllLiked(id);
    }

    public List<Cards> fetchMatches(String userId) throws UserNotFoundException {
        //userId = USER_ID;
        Cards userCard = fetchUser(userId);
        List<String> listCards = new ArrayList<>();
        userCard.getLikes().forEach(item -> listCards.add(item));

        List<String> userIdList = new ArrayList<>();
        userIdList.add(userCard.getId());
        return cardRepository.fetchMatches(listCards, userIdList);
    }

    public Cards addLike(Like like) throws UserNotFoundException {
        Optional<Cards> userCard = cardRepository.findById(USER_ID);

        if(userCard.isPresent()){
            Cards currentCard = userCard.get();
            currentCard.addLike(like);
            return cardRepository.save(currentCard);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    public Cards removeLike(Like like) throws UserNotFoundException {

        Optional<Cards> userCard = cardRepository.findById(USER_ID);

        if(userCard.isPresent()){
            Cards currentCard = userCard.get();
            currentCard.removeLike(like);
            return cardRepository.save(currentCard);
        }
        else throw new UserNotFoundException("User not found");

    }

    public Cards save(){
        cardRepository.save(new Cards("Bruno", "https://github.com/bvrsilva1/bvrsilva1.github.io/blob/master/img/bruno.png", "Software Engineer, guitarist, runner and beer enthusiast"));
        cardRepository.save(new Cards("Elon Musk", "https://www.gstatic.com/tv/thumb/persons/487130/487130_v9_ba.jpg", "business magnate, industrial designer, engineer, and philanthropist"));
        cardRepository.save(new Cards("Barack Obama", "https://www.biography.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cg_face%2Cq_auto:good%2Cw_300/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg", "an American politician and attorney who served as the 44th president of the United States from 2009 to 2017"));
        cardRepository.save(new Cards("\n" +
                "Ada Lovelace", "https://www.gstatic.com/tv/thumb/persons/487130/487130_v9_ba.jpg", "an English mathematician and writer, chiefly known for her work on Charles Babbage's proposed mechanical general-purpose computer.  often regarded as the first to recognise the full potential of computers and one of the first computer programmers."));
        cardRepository.save(new Cards("Ronaldinho Gaucho", "https://static01.nyt.com/images/2013/07/18/sports/soccer/18copa/18copa-jumbo.jpg?quality=90&auto=webp", "Brazilian former professional footballer and ambassador for Barcelona."));
        cardRepository.save(new Cards("Lemmy", "https://images.squarespace-cdn.com/content/v1/536ea5cbe4b074eafa4feea0/1589570464784-DISY0IQPY27USC50K0VR/ke17ZwdGBToddI8pDm48kGDpvalPb1SqHoCn1hwN0Y57gQa3H78H3Y0txjaiv_0fDoOvxcdMmMKkDsyUqMSsMWxHk725yiiHCCLfrh8O1z5QHyNOqBUUEtDDsRWrJLTmQPoRzxSr1hzN-vPBHt7YyLLXgctAyUJRqJUUGWVDK_ZzIgvsybGcZEPqUYiXY8im/Rapport_Lemmy.jpg?format=500w", "Founder, lead singer, bassist, and primary songwriter of the rock band Motörhead."));
        cardRepository.save(new Cards("Michael Scott", "https://vignette.wikia.nocookie.net/theoffice/images/8/84/Datemike1.jpg/revision/latest?cb=20100327172902", "Regional manager. Nice to meet me!"));
        cardRepository.save(new Cards("\n" +
                "Dwight Schrute", "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/gettyimages-152927591-1560886569.jpg", "Of course Martial arts training is relevant… Uh, I know about a billion Asians that would beg to differ… You know what, you can go to hell, and I will see you there. Burning!"));
        cardRepository.save(new Cards("Claudia Tiedemann", "https://metawitches.files.wordpress.com/2019/06/dark-s2ep2-adult-claudia-1987.png?w=656", "Time traveler. Sic Mundus"));
        cardRepository.save(new Cards("\n" +
                "Jonas Kahnwald", "https://www.gstatic.com/tv/thumb/persons/487130/487130_v9_ba.jpg", "born somewhere in 2002 or 2003 to Michael and Hannah Kahnwald"));
        cardRepository.save(new Cards("\n" +
                "Juliana Crain ", "https://i.pinimg.com/236x/8e/55/aa/8e55aa8bf0e16f363bcb9602e6ec76d6.jpg", "American who has lived most of her life in the Japanese Pacific States as a subject of the Japanese Empire, but has gotten involved with the Resistance and the Man in the High Castle and may prove key in preventing the Greater Nazi Reich from laying waste to the entire world."));
        cardRepository.save(new Cards("Adrian Balboa", "https://i.pinimg.com/originals/dd/8d/52/dd8d52570bf0e5d86dbd0ee6087960fa.jpg", "Win. I want you to win!"));
        cardRepository.save(new Cards("Princess Leia Organa", "https://i.insider.com/5878a396dd0895ed118b49a4?width=1100&format=jpeg&auto=webp", "member of the Imperial Senate and an agent of the Rebel Alliance"));
        Cards pinehead = new Cards("Pinhead", "https://www.clivebarker.info/pinheader.jpg", "The box. You opened it. We came.");
        return cardRepository.save(pinehead);

    }

}
