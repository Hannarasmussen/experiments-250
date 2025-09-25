package com.example.demo.experiment2.ex5;

import redis.clients.jedis.UnifiedJedis;

public class redisDump {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        String pollId = "03ebcb7b-bd69-440b-924e-f5b7d664af7b";

        String pollMetaKey = "poll:" + pollId + ":meta";
        String pollOptionsKey = "poll:" + pollId + ":options";

        jedis.hset(pollMetaKey, "title", "Pineapple on Pizza?");
        System.out.println("Poll title: " + jedis.hget(pollMetaKey, "title"));

        jedis.hset(pollOptionsKey, "Yes, yammy!", "269");
        jedis.hset(pollOptionsKey, "Mamma mia, nooooo!", "268");
        jedis.hset(pollOptionsKey, "I do not really care", "42");
        System.out.println("Options and votes:");
        jedis.hgetAll(pollOptionsKey).forEach((option, votes) -> {
        System.out.println(" " + option + " → " + votes);
        });

        jedis.hincrBy(pollOptionsKey, "Yes, yammy!", 1);

        System.out.println("Updated poll:");
        jedis.hgetAll(pollOptionsKey).forEach((option, votes) -> {
        System.out.println(" " + option + " → " + votes);
        });
        jedis.close();

    }
}