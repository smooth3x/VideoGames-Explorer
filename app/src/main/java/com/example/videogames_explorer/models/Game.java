package com.example.videogames_explorer.models;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {
    private String name;
    private String deck;
    private String original_release_date;
    private List<Platform> platforms;
    private Image image;

    public Game(String name, String deck, String original_release_date, List<Platform> platforms, Image image) {
        this.name = name;
        this.deck = deck;
        this.original_release_date = original_release_date;
        this.platforms = platforms;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }

    public String getOriginalReleaseDate() {
        return original_release_date;
    }

    public void setOriginalReleaseDate(String original_release_date) {
        this.original_release_date = original_release_date;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static class Platform implements Serializable {
        private String name;

        public Platform(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Image implements Serializable {
        private String original_url;

        public Image(String imageUrl) {
            this.original_url = imageUrl;
        }

        public String getOriginalUrl() {
            return original_url;
        }

        public void setOriginalUrl(String original_url) {
            this.original_url = original_url;
        }
    }
}
