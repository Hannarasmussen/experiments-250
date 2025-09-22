package com.example.demo.experiment2.entities;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant publishedAt;

    @ManyToOne
    private User voter;

    @ManyToMany
    private Set<VoteOption> votesOn;

    private boolean isValid;

    public Vote() {
        this.publishedAt = Instant.now();
        votesOn = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public User getUser() {
        return voter;
    }

    public void setUser(User voter) {
        this.voter = voter;
    }

    public Set<VoteOption> getVoteOptions() {
        return votesOn;
    }

    public void setVoteOptions(Set<VoteOption> votesOn) {
        this.votesOn = votesOn;
    }

    public void setVoteOption(VoteOption option) {
        this.votesOn = new LinkedHashSet<>();
        this.votesOn.add(option);
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

}
