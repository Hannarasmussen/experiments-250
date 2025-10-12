package com.example.demo.experiment2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.managers.PollManager;

@Component
public class VoteEventListener {

    @Autowired
    private PollManager pollManager;

    @RabbitListener(bindings = @org.springframework.amqp.rabbit.annotation.QueueBinding(value = @org.springframework.amqp.rabbit.annotation.Queue(value = "votes-queue", durable = "true"), exchange = @org.springframework.amqp.rabbit.annotation.Exchange(value = RabbitConfig.EXCHANGE_NAME, type = "topic"), key = "poll.*.vote"))
    public void handleVoteEvent(String message) {
        System.out.println(" [x] Received vote event: " + message);

        String[] parts = message.split(" ");
        Long pollId = Long.parseLong(parts[2]);

        Poll poll = pollManager.getPoll(pollId);
        if (poll != null) {
            pollManager.updatePoll(poll);
            System.out.println("Poll " + pollId + " updated in database.");
        }
    }
}
