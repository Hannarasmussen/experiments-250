package com.example.demo.experiment2.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.managers.PollManager;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {
    private PollManager pollManager;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange exchange;

    public VoteController(@Autowired PollManager pollManager, @Autowired RabbitTemplate rabbitTemplate,
            @Autowired TopicExchange exchange) {
        this.pollManager = pollManager;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable Long id) {
        Vote vote = pollManager.getVote(id);
        if (vote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vote);
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getVotes() {
        return ResponseEntity.ok(pollManager.getAllVotes());
    }

    // @PostMapping
    // public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
    // Long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    // while (!pollManager.addVote(vote))
    // id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

    // vote.setId(id);
    // String routingKey = "poll." +
    // vote.getVoteOptions().iterator().next().getPoll().getId() + ".vote";
    // String message = "New vote for poll " + routingKey;
    // rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    // System.out.println(" [x] Sent vote event: '" + message + "'");
    // return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    // }

    @PostMapping("/{pollName}/vote")
    public String vote(@PathVariable String pollName, @RequestBody String voteOption) {
        String routingKey = "poll." + pollName + ".votes";
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey, voteOption);
        System.out.println("Published vote for " + pollName + ": " + voteOption);
        return "Vote sent for " + pollName;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable Long id, @RequestBody Vote vote) {
        Vote existingVote = pollManager.getVote(id);
        if (existingVote == null) {
            return ResponseEntity.notFound().build();
        }
        pollManager.updateVote(existingVote);
        return ResponseEntity.ok(existingVote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vote> deleteVote(@PathVariable Long id) {
        Vote existingVote = pollManager.getVote(id);
        if (existingVote == null) {
            return ResponseEntity.notFound().build();
        }
        boolean removed = pollManager.removeVote(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingVote);
    }

}
