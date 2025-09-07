package com.example.demo.experiment2.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class VoteController {
    private PollManager pollManager;

    public VoteController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable int id) {
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

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        int id = UUID.randomUUID().hashCode();
        while (!pollManager.addVote(id, vote))
            id = UUID.randomUUID().hashCode();

        vote.setId(id); // <-- store the id in the Vote
        return ResponseEntity.status(HttpStatus.CREATED).body(vote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable int id, @RequestBody Vote vote) {
        Vote existingVote = pollManager.getVote(id);
        if (existingVote == null) {
            return ResponseEntity.notFound().build();
        }
        pollManager.updateVote(id, vote);
        return ResponseEntity.ok(vote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vote> deleteVote(@PathVariable int id) {
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
