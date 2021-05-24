package com.thedariusz.warnme.twitter;

import java.util.List;

public class TweetDto {

    private final String tweetId;
    private final String text;
    private final AuthorDto author;
    private final String creationDate;
    private final List<String> mediaList;
    private final List<String> hashTags;

    public static TweetDtoBuilder builder() {
        return new TweetDtoBuilder();
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

    public static final class TweetDtoBuilder {
        private String tweetId;
        private String text;
        private AuthorDto author;
        private String creationDate;
        private List<String> mediaList;
        private List<String> hashTags;

        private TweetDtoBuilder() {
        }

        public TweetDtoBuilder withTweetId(String tweetId) {
            this.tweetId = tweetId;
            return this;
        }

        public TweetDtoBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public TweetDtoBuilder withAuthor(AuthorDto author) {
            this.author = author;
            return this;
        }

        public TweetDtoBuilder withCreationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public TweetDtoBuilder withMediaList(List<String> mediaList) {
            this.mediaList = mediaList;
            return this;
        }

        public TweetDtoBuilder withHashTags(List<String> hashTags) {
            this.hashTags = hashTags;
            return this;
        }

        public TweetDto build() {
            return new TweetDto(tweetId, text, author, creationDate, mediaList, hashTags);
        }

        public TweetDto fakeTweet(String id, String creationDate, String twitterUserId, List<String> hashTags, String text) {
            return new TweetDto(id, text, AuthorDto.fake(twitterUserId), creationDate,  List.of("url1", "url2"), hashTags);
        }
    }
}
