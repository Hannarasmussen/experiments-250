package com.example.demo.experiment2.ex5;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.UnifiedJedis;

public class cash {

    public static void main(String[] args) {
         UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        String pollId = "03ebcb7b-bd69-440b-924e-f5b7d664af7a";

        Map<String, Integer> dbResults = new HashMap<>();

        dbResults.put("Yes, yammy!", 269);
        dbResults.put("Mamma mia, nooooo!", 268);
        dbResults.put("I do not really care", 42);

        Map<String, String> cachedResults = jedis.hgetAll(pollId);
        System.out.println("Cached results: " + cachedResults);

        if (!cachedResults.isEmpty()) {
            System.out.println("Cache hit!");
            System.out.println("Poll results from cache: " + cachedResults);
        } else {
            System.out.println("Cache miss! Loading from DB");

            for (Map.Entry<String, Integer> entry : dbResults.entrySet()) {
                jedis.hset(pollId, entry.getKey(), entry.getValue().toString());
            }

            jedis.expire(pollId, 60);

            System.out.println("Poll results stored in cache: " + dbResults);
        }

        String votedOption = "Yes, yammy!";
        jedis.hincrBy(pollId, votedOption, 1);
        System.out.println("Vote added for: " + votedOption);

        Map<String, String> updatedResults = jedis.hgetAll(pollId);
        System.out.println("Updated poll results from cache: " + updatedResults);

        jedis.close();

    }
}
