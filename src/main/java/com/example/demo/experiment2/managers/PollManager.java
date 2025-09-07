package com.example.demo.experiment2.managers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.entities.User;
import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.entities.VoteOption;

@Component
public class PollManager {
    private Map<Integer, Poll> polls = new HashMap<>();
    private Map<Integer, User> users = new HashMap<>();
    private Map<Integer, Vote> votes = new HashMap<>();
    private Map<Integer, VoteOption> voteOptions = new HashMap<>();

    // Polls
    public Poll getPoll(int id) {
        return polls.get(id);
    }

    public boolean addPoll(int id, Poll poll) {
        if (polls.containsKey(id))
            return false;
        polls.put(id, poll);
        return true;
    }

    public Poll updatePoll(int id, Poll poll) {
        if (!polls.containsKey(id))
            return null;
        polls.put(id, poll);
        return poll;
    }

    public boolean removePoll(int id) {
        return polls.remove(id) != null;
    }

    public List<Poll> getAllPolls() {
        return new ArrayList<>(polls.values());
    }

    public Instant getValidUntil(int id) {
        Poll poll = polls.get(id);
        return poll != null ? poll.getValidUntil() : null;
    }

    public Boolean setValidUntil(int id, Instant validUntil) {
        Poll poll = polls.get(id);
        if (poll == null) {
            return false;
        }
        poll.setValidUntil(validUntil);
        return true;
    }

    // Users
    public boolean addUser(int id, User user) {
        if (users.containsKey(id))
            return false;
        users.put(id, user);
        return true;
    }

    public User getUser(int id) {
        return users.get(id);
    }

    public User updateUser(int id, User user) {
        if (!users.containsKey(id))
            return null;

        User existingUser = users.get(id);
        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return existingUser;
    }

    public boolean removeUser(int id) {
        return users.remove(id) != null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    // Votes
    public boolean addVote(int id, Vote vote) {
        if (votes.containsKey(id))
            return false;
        votes.put(id, vote);
        return true;
    }

    public Vote getVote(int id) {
        return votes.get(id);
    }

    public boolean updateVote(int id, Vote vote) {
        if (!votes.containsKey(id))
            return false;
        votes.put(id, vote);
        return true;
    }

    public boolean removeVote(int id) {
        return votes.remove(id) != null;
    }

    public List<Vote> getAllVotes() {
        return new ArrayList<>(votes.values());
    }

    // Vote Options
    public VoteOption getVoteOption(int optionId) {
        return voteOptions.get(optionId);
    }

    public boolean addVoteOption(int id, VoteOption option) {
        if (voteOptions.containsKey(id))
            return false;
        voteOptions.put(id, option);
        return true;
    }

    public boolean updateVoteOption(int id, VoteOption option) {
        if (!voteOptions.containsKey(id))
            return false;
        voteOptions.put(id, option);
        return true;
    }

    public boolean removeVoteOption(int id) {
        return voteOptions.remove(id) != null;
    }

}
