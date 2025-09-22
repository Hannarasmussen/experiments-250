package com.example.demo.experiment2.controllers;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.managers.PollManager;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/polls")
@CrossOrigin(origins = "http://localhost:5173")
public class PollController {
    private final PollManager pollManager;

    public PollController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        Poll poll = pollManager.getPoll(id);
        if (poll == null) {
            return ResponseEntity.notFound().build();
        }
        if (poll.getValidUntil() != null && poll.getValidUntil().isBefore(Instant.now())) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        return ResponseEntity.ok(poll);
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        System.out.println("Received Poll: " + poll.getVoteOptions().get(0).getCaption());
        Long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        poll.setId(id);
        while (!pollManager.addPoll(poll)) {
            id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
            poll.setId(id);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(poll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long id, @RequestBody Poll poll) {
        Poll existingPoll = pollManager.getPoll(id);
        if (existingPoll == null) {
            return ResponseEntity.notFound().build();
        }
        pollManager.updatePoll(poll);
        return ResponseEntity.ok(poll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Poll> deletePoll(@PathVariable Long id) {
        boolean removed = pollManager.removePoll(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/valid-until")
    public ResponseEntity<Instant> getValidUntil(@PathVariable Long id) {
        Instant validUntil = pollManager.getValidUntil(id);
        if (validUntil == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(validUntil);
    }

    @PostMapping("/{id}/valid-until")
    public ResponseEntity<Boolean> setValidUntil(@PathVariable Long id, @RequestBody Instant validUntil) {
        boolean result = pollManager.setValidUntil(id, validUntil);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() {
        java.util.List<Poll> polls = pollManager.getAllPolls();
        return ResponseEntity.ok(polls);
    }
}