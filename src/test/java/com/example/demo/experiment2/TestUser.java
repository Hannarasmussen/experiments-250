package com.example.demo.experiment2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.entities.User;
import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.entities.VoteOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUser {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testScenario() {

        String baseUrl = "http://localhost:" + port;

        // Create a new user
        User user = new User();
        user.setUserName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("secret");

        String url = baseUrl + "/users";
        ResponseEntity<User> createdUser = restTemplate.postForEntity(url, user, User.class);

        assertEquals(HttpStatus.OK, createdUser.getStatusCode());
        assertNotNull(createdUser.getBody());

        // list all users
        ResponseEntity<User[]> allUsers = restTemplate.getForEntity(baseUrl + "/users", User[].class);
        assertEquals(HttpStatus.OK, allUsers.getStatusCode());
        assertNotNull(allUsers.getBody());
        assertEquals(1, allUsers.getBody().length);
        assertEquals("Alice", allUsers.getBody()[0].getUserName());

        // create new user
        User newUser = new User();
        newUser.setUserName("Bob");
        newUser.setEmail("bob@example.com");
        newUser.setPassword("secret");

        ResponseEntity<User> createdUser2 = restTemplate.postForEntity(baseUrl + "/users", newUser, User.class);
        assertEquals(HttpStatus.OK, createdUser.getStatusCode());
        assertNotNull(createdUser.getBody());

        // list all users
        ResponseEntity<User[]> allUsersAfter = restTemplate.getForEntity(baseUrl + "/users", User[].class);
        assertEquals(HttpStatus.OK, allUsersAfter.getStatusCode());
        assertNotNull(allUsersAfter.getBody());
        assertEquals(2, allUsersAfter.getBody().length);

        // user1 creates new poll
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

        ResponseEntity<Poll> createdPoll = restTemplate.postForEntity(baseUrl + "/polls", poll, Poll.class);
        assertEquals(HttpStatus.CREATED, createdPoll.getStatusCode());
        assertNotNull(createdPoll.getBody());

        int pollId = createdPoll.getBody().getId();

        // list all polls
        ResponseEntity<Poll[]> allPolls = restTemplate.getForEntity(baseUrl + "/polls", Poll[].class);
        assertEquals(HttpStatus.OK, allPolls.getStatusCode());
        assertNotNull(allPolls.getBody());
        assertEquals(1, allPolls.getBody().length);

        // user2 votes on poll
        VoteOption voteOption = new VoteOption();
        ResponseEntity<VoteOption> voteOptionResponse = restTemplate.postForEntity(baseUrl + "/voteOptions",
                voteOption, VoteOption.class);
        assertEquals(HttpStatus.CREATED, voteOptionResponse.getStatusCode());

        Vote vote = new Vote();
        vote.setOptions(Arrays.asList(voteOptionResponse.getBody()));
        ResponseEntity<Vote> voteResponse = restTemplate.postForEntity(baseUrl + "/votes",
                vote, Vote.class);
        vote = voteResponse.getBody();
        assertEquals(HttpStatus.CREATED, voteResponse.getStatusCode());

        // user2 changes answer on poll
        vote.setOptions(Arrays.asList());
        ResponseEntity<Vote> changedVoteResponse = restTemplate.exchange(baseUrl + "/votes/" + vote.getId(),
                HttpMethod.PUT, new HttpEntity<>(vote), Vote.class);
        assertEquals(HttpStatus.OK, changedVoteResponse.getStatusCode());

        // list votes
        ResponseEntity<Vote[]> allVotes = restTemplate.getForEntity(baseUrl +
                "/votes",
                Vote[].class);
        assertEquals(HttpStatus.OK, allVotes.getStatusCode());
        assertNotNull(allVotes.getBody());
        assertEquals(1, allVotes.getBody().length);

        // delete poll
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(baseUrl + "/polls/" + pollId, HttpMethod.DELETE,
                null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // list polls
        ResponseEntity<Poll[]> allPollsAfterDelete = restTemplate.getForEntity(baseUrl + "/polls", Poll[].class);
        assertEquals(HttpStatus.OK, allPollsAfterDelete.getStatusCode());
        assertNotNull(allPollsAfterDelete.getBody());
        assertEquals(0, allPollsAfterDelete.getBody().length);
    }
}
