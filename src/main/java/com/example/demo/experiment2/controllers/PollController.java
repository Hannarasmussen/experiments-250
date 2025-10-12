package com.example.demo.experiment2.controllers;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

@RestController
@RequestMapping("/polls")
@CrossOrigin(origins = "http://localhost:5173")
public class PollController {
    private final PollManager pollManager;

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private TopicExchange exchange;

    private final Map<String, String> polls = new HashMap<>();

    public PollController(@Autowired PollManager pollManager, @Autowired RabbitAdmin rabbitAdmin,
            @Autowired TopicExchange exchange) {
        this.pollManager = pollManager;
        this.rabbitAdmin = rabbitAdmin;
        this.exchange = exchange;
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

    // @PostMapping
    // public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {

    // System.out.println("Received Poll: " +
    // poll.getVoteOptions().get(0).getCaption());

    // Long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    // poll.setId(id);
    // while (!pollManager.addPoll(poll)) {

    // id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    // poll.setId(id);
    // }

    // String routingKey = "poll." + poll.getId() + ".created";
    // String message = "Poll created: " + poll.getQuestion();
    // rabbitTemplate.convertAndSend(exchange.getName(), routingKey, message);
    // System.out.println(" [x] Sent event: '" + message + "'");

    // return ResponseEntity.status(HttpStatus.CREATED).body(poll);
    // }

    @PostMapping
    public String createPoll(@RequestBody String pollName) {
        String queueName = "poll." + pollName + ".votes";

        // Create and bind queue
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queueName));

        polls.put(pollName, queueName);
        System.out.println("Created poll and queue: " + queueName);

        return "Poll created: " + pollName;
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

    @GetMapping
    public Set<String> getPolls() {
        return polls.keySet();
    }
}