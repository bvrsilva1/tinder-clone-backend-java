package com.bruno.tinderclone.utils;

import com.bruno.tinderclone.model.Cards;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ConvertPageToCard {

    public static List<Cards> convert(Page<Cards> page){
        List<Cards> list = new ArrayList<>();

        for(Cards card : page.getContent()){
            list.add(card);
        }

        return list;
    }

}
