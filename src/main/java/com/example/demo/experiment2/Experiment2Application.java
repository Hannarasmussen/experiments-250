// package com.example.demo.experiment2;

// import java.math.BigDecimal;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;

// import org.hibernate.cfg.JdbcSettings;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// import com.example.demo.experiment2.entities.Poll;
// import com.example.demo.experiment2.entities.User;
// import com.example.demo.experiment2.entities.Vote;
// import com.example.demo.experiment2.entities.VoteOption;

// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.PersistenceConfiguration;

// @SpringBootApplication
// public class Experiment2Application {
//     public static void main(String[] args) {
//         EntityManagerFactory emf = new PersistenceConfiguration("test2")
//                 .managedClass(User.class)
//                 .managedClass(Poll.class)
//                 .managedClass(Vote.class)
//                 .managedClass(VoteOption.class)
//                 // corresponds to 'jakarta.persistence.jdbc.url' in the persistence.xml
//                 .property(PersistenceConfiguration.JDBC_URL,
//                         "jdbc:h2:file:./polls/polls")

//                 // other properties accordingly
//                 .property(PersistenceConfiguration.JDBC_USER, "sa")
//                 .property(PersistenceConfiguration.JDBC_PASSWORD, "")
//                 .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION,
//                         "update") // Recreate the
//                 // database fresh; DO
//                 // NOT USE IN
//                 // PRODUCTION
//                 // Hibernate specific properties are found in the JdbcSettings class
//                 .property(JdbcSettings.SHOW_SQL, true)
//                 .property(JdbcSettings.FORMAT_SQL, true)
//                 .property(JdbcSettings.HIGHLIGHT_SQL, true)
//                 .createEntityManagerFactory();
//         emf.runInTransaction(entityManager -> {
//             User b1 = new User();
//             b1.setUsername("bob");
//             b1.setEmail("bob@example.com");
//             b1.setPassword("password");
//             Poll p1 = new Poll();
//             p1.setQuestion("What is your favorite programming language?");
//             p1.setCreator(b1);
//             p1.setValidUntil(java.time.Instant.now().plusSeconds(86400));
//             p1.setVoteOptions(new java.util.LinkedHashSet<VoteOption>());
//             VoteOption vo1 = new VoteOption()
//             VoteOption vo2 = new VoteOption()
//                     .setOption("Python")
//                     .setPoll(p1);
//             VoteOption vo3 = new VoteOption()
//                     .setOption("JavaScript")
//                     .setPoll(p1);
//             Vote v1 = new Vote()
//                     .setUser(b1)
//                     .setPoll(p1)
//                     .setVoteOption(vo1);
//             Vote v2 = new Vote()
//                     .setUser(b2)
//                     .setPoll(p2)
//                     .setVoteOption(vo2);
//             Vote v3 = new Vote();

//             entityManager.persist(b1);
//             entityManager.persist(b2);
//             entityManager.persist(b3);
//             entityManager.persist(p1);
//             entityManager.persist(p2);
//             entityManager.persist(p3);
//             entityManager.persist(vo1);
//             entityManager.persist(vo2);
//             entityManager.persist(vo3);
//             entityManager.persist(v1);
//             entityManager.persist(v2);
//             entityManager.persist(v3);
//         });
//         // try {
//         // Connection conn = DriverManager.getConnection("jdbc:h2:file:./polls/polls",
//         // "sa", "");
//         // Statement stmt = conn.createStatement();
//         // ResultSet rs = stmt.executeQuery("SELECT * FROM users");
//         // ResultSet rs = stmt.executeQuery("SELECT * FROM polls");
//         // ResultSet rs = stmt.executeQuery("SELECT * FROM vote_options");
//         // ResultSet rs = stmt.executeQuery("SELECT * FROM votes");
//         // while (rs.next()) {
//         // System.out.println(rs.getString("username"));
//         // }
//         // System.out.println(rs);
//         // } catch (Exception e) {

//         // }
//         SpringApplication app = new SpringApplication(Experiment2Application.class);
//         app.addInitializers(applicationContext -> {
//             applicationContext.getBeanFactory().registerSingleton("emf", emf);
//         });
//         app.run(args);
//     }
// }


package com.example.demo.experiment2;

import org.hibernate.cfg.JdbcSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.entities.User;
import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.entities.VoteOption;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;

@SpringBootApplication
public class Experiment2Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = new PersistenceConfiguration("test2")
                .managedClass(User.class)
                .managedClass(Poll.class)
                .managedClass(Vote.class)
                .managedClass(VoteOption.class)
                .property(PersistenceConfiguration.JDBC_URL, "jdbc:h2:file:./polls/polls")
                .property(PersistenceConfiguration.JDBC_USER, "sa")
                .property(PersistenceConfiguration.JDBC_PASSWORD, "")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "update")
                .property(JdbcSettings.SHOW_SQL, true)
                .property(JdbcSettings.FORMAT_SQL, true)
                .property(JdbcSettings.HIGHLIGHT_SQL, true)
                .createEntityManagerFactory();

        emf.runInTransaction(entityManager -> {

            // Users
            User b1 = new User();
            b1.setUsername("bob");
            b1.setEmail("bob@example.com");
            b1.setPassword("password");
            User b2 = new User();
            b2.setUsername("alice");
            b2.setEmail("alice@example.com");
            b2.setPassword("1234");

            // Polls
            Poll p1 = new Poll();
            p1.setQuestion("What is your favorite programming language?");
            p1.setCreatedBy(b1);
            p1.setValidUntil(java.time.Instant.now().plusSeconds(86400));

            // Vote options
            VoteOption vo1 = new VoteOption();
            vo1.setCaption("Java");
            vo1.setPoll(p1);
            VoteOption vo2 = new VoteOption();
            vo2.setCaption("Python");
            vo2.setPoll(p1);
            VoteOption vo3 = new VoteOption();
            vo3.setCaption("JavaScript");
            vo3.setPoll(p1);

            // Add vote options to poll
            p1.getVoteOptions().add(vo1);
            p1.getVoteOptions().add(vo2);
            p1.getVoteOptions().add(vo3);

            // Votes
            Vote v1 = new Vote();
            v1.setUser(b1);
            v1.setVoteOption(vo1);
            Vote v2 = new Vote();
            v2.setUser(b2);
            v2.setVoteOption(vo2);

            entityManager.persist(b1);
            entityManager.persist(b2);
            entityManager.persist(p1);
            entityManager.persist(vo1);
            entityManager.persist(vo2);
            entityManager.persist(vo3);
            entityManager.persist(v1);
            entityManager.persist(v2);
        });

        
        SpringApplication app = new SpringApplication(Experiment2Application.class);
        app.addInitializers(applicationContext -> applicationContext.getBeanFactory().registerSingleton("emf", emf));
        app.run(args);
    }
}
