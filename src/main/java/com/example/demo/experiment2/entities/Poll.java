package com.example.demo.experiment2.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Poll {

    private int id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private boolean isPrivate;
    private List<VoteOption> voteOptions;

    public Poll() {
        this.publishedAt = Instant.now();
        this.validUntil = Instant.now().plusSeconds(86400);
        this.isPrivate = false;
        this.voteOptions = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }
    public boolean setVoteOptions(List<VoteOption> options) {
        voteOptions.clear();
        return voteOptions.addAll(options);
    }
}
