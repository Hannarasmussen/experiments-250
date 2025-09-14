package com.example.demo.experiment2.controllers;

import java.util.UUID;

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

import com.example.demo.experiment2.entities.VoteOption;
import com.example.demo.experiment2.managers.PollManager;

@RestController
@RequestMapping("/voteOptions")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteOptionController {
    private PollManager pollManager;

    public VoteOptionController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteOption> getVoteOption(@PathVariable int id) {
        VoteOption voteOption = pollManager.getVoteOption(id);
        if (voteOption == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(voteOption);
    }

    @PostMapping
    public ResponseEntity<VoteOption> createVoteOption(@RequestBody VoteOption voteOption) {
        int id = UUID.randomUUID().hashCode();
        voteOption.setId(id);
        while (!pollManager.addVoteOption(id, voteOption)) {
            id = UUID.randomUUID().hashCode();
            voteOption.setId(id);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(voteOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoteOption> updateVoteOption(@PathVariable int id, @RequestBody VoteOption voteOption) {
        VoteOption existingVoteOption = pollManager.getVoteOption(id);
        if (existingVoteOption == null) {
            return ResponseEntity.notFound().build();
        }
        pollManager.updateVoteOption(id, voteOption);
        return ResponseEntity.ok(voteOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VoteOption> deleteVoteOption(@PathVariable int id) {
        VoteOption existingVoteOption = pollManager.getVoteOption(id);
        if (existingVoteOption == null) {
            return ResponseEntity.notFound().build();
        }
        boolean removed = pollManager.removeVoteOption(id);
        if (!removed) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingVoteOption);
    }
}
