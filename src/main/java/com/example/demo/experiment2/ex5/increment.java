package com.example.demo.experiment2.ex5;
import redis.clients.jedis.Jedis;

public class increment {
    public static void main(String[] args) {
       try (Jedis jedis = new Jedis("localhost", 6379)) {

        String key = "logged_in_users";

        jedis.del(key);

        jedis.sadd(key, "alice");
        System.out.println("Users: " + jedis.smembers(key));

        jedis.sadd(key, "bob");
        System.out.println("Users: " + jedis.smembers(key));

        jedis.srem(key, "alice");
        System.out.println("Users: " + jedis.smembers(key));

        jedis.sadd(key, "eve");
        System.out.println("Users: " + jedis.smembers(key));

        jedis.close();
    }
}
    
}
