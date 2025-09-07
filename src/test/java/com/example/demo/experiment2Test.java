package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.entities.User;
import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.entities.VoteOption;
import com.example.demo.experiment2.managers.PollManager;

public class experiment2Test {

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("Alice");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        int id = 1;

        PollManager pollManager = new PollManager();
        boolean created = pollManager.addUser(id, user);

        assertTrue(created);
        assertEquals(user, pollManager.getUser(id));
        assertEquals("Alice", pollManager.getUser(id).getUserName());
        assertEquals("test@example.com", pollManager.getUser(id).getEmail());
        assertEquals("password123", pollManager.getUser(id).getPassword());
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUserName("Alice");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        int id = 1;

        PollManager pollManager = new PollManager();
        pollManager.addUser(id, user);

        User retrieved = pollManager.getUser(id);

        assertEquals(user, retrieved);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUserName("Alice");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        int id = 1;

        PollManager pollManager = new PollManager();
        pollManager.addUser(id, user);

        User newUser = new User();
        newUser.setUserName("Alice Updated");
        newUser.setEmail("test_updated@example.com");
        newUser.setPassword("password1234");

        User updated = pollManager.updateUser(id, newUser);

        assertEquals(user, updated);
        assertEquals("Alice Updated", updated.getUserName());
        assertEquals("test_updated@example.com", updated.getEmail());
        assertEquals("password1234", updated.getPassword());
    }

    @Test
    public void createPoll() {

        Poll poll = new Poll();
        poll.setIsPrivate(false);
        poll.setQuestion("What is your favorite programming language?");

        VoteOption voteOption1 = new VoteOption();
        voteOption1.setCaption("Java");
        VoteOption voteOption2 = new VoteOption();
        voteOption2.setCaption("Python");
        VoteOption voteOption3 = new VoteOption();
        voteOption3.setCaption("JavaScript");

        poll.setVoteOptions(new ArrayList<>(Arrays.asList(voteOption1, voteOption2, voteOption3)));

        assertEquals("What is your favorite programming language?", poll.getQuestion());
        assertFalse(poll.getIsPrivate());
        assertEquals("Java", poll.getVoteOptions().get(0).getCaption());
        assertEquals("Python", poll.getVoteOptions().get(1).getCaption());
        assertEquals("JavaScript", poll.getVoteOptions().get(2).getCaption());
    }

    @Test
    public void testVote() {
        Poll poll = new Poll();
        poll.setIsPrivate(false);
        poll.setQuestion("What is your favorite programming language?");

        VoteOption voteOption1 = new VoteOption();
        voteOption1.setCaption("Java");
        VoteOption voteOption2 = new VoteOption();
        voteOption2.setCaption("Python");
        VoteOption voteOption3 = new VoteOption();
        voteOption3.setCaption("JavaScript");

        poll.setVoteOptions(new ArrayList<>(Arrays.asList(voteOption1, voteOption2, voteOption3)));

        User user = new User();
        user.setUserName("Alice");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        int userId = 1;

        new PollManager().addUser(userId, user);

        Vote vote = new Vote();

        vote.setUser(user);
        vote.setIsValid(true);
        vote.setOptions(Arrays.asList(voteOption1, voteOption2));

        assertEquals("Java", vote.getOptions().get(0).getCaption());
        assertEquals("Python", vote.getOptions().get(1).getCaption());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            vote.getOptions().get(2).getCaption();
        });
    }



}