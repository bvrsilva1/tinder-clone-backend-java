package com.bruno.tinderclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "cards")
public class Cards {

    @Id
    public String id;
    private String name;
    private String image;
    private String bio;
    Set<String> likes = new HashSet<>();
    Set<String> matches = new HashSet<>();

    public Cards(String name, String image, String bio){
        this.name = name;
        this.image = image;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getBio() {
        return bio;
    }

    public Set<String> getLikes() {
        return likes;
    }

    public Set<String> getMatches() {
        return matches;
    }

    public void addLike(Like like){
        likes.add(like.getLikedId());
    }

    public void removeLike(Like like) {
        likes.remove(like.getLikedId());
    }

}
