package com.example.demo.experiment2.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Vote {

    private int id;
    private Instant publishedAt;
    private User user;
    private List<VoteOption> options;
    private boolean isValid;

    public Vote() {
        this.publishedAt = Instant.now();
        options = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public List<VoteOption> getOptions() {
        return options;
    }
    public void setOptions(List<VoteOption> options) {
        this.options = options;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    

}
