package com.bruno.tinderclone.model;

import lombok.*;

@NoArgsConstructor
public class Like {

    private String likedId;

    public Like(String likedId){
        this.likedId = likedId;
    }

    public String getLikedId() {
        return likedId;
    }

}
