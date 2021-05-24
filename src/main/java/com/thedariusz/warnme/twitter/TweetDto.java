package com.thedariusz.warnme.twitter;

import java.util.List;

public class TweetDto {

    private final String tweetId;
    private final String text;
    private final AuthorDto author;
    private final String creationDate;
    private final List<String> mediaList;
    private final List<String> hashTags;

    public static TweetDto fakeTweet(String id, String creationDate, String twitterUserId, List<String> hashTags) {
        return new TweetDto(id, "testowy tweet numer "+id, AuthorDto.fake(twitterUserId), creationDate,  List.of("url1", "url2"), hashTags);
    }

    private TweetDto(String tweetId, String text, AuthorDto author, String creationDate, List<String> mediaList, List<String> hashTags) {
        this.tweetId = tweetId;
        this.text = text;
        this.author = author;
        this.creationDate = creationDate;
        this.mediaList = List.copyOf(mediaList);
        this.hashTags = List.copyOf(hashTags);
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getText() {
        return text;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public List<String> getMediaList() {
        return mediaList;
    }

    public List<String> getHashTags() {
        return hashTags;
    }
}