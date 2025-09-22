// package com.example.demo.experiment2.managers;

// import java.time.Instant;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import com.example.demo.experiment2.entities.Poll;
// import com.example.demo.experiment2.entities.User;
// import com.example.demo.experiment2.entities.Vote;
// import com.example.demo.experiment2.entities.VoteOption;

// import jakarta.persistence.EntityManagerFactory;

// @Component
// public class PollManager {
//     private Map<Long, Poll> polls = new HashMap<>();
//     private Map<Long, User> users = new HashMap<>();
//     private Map<Long, Vote> votes = new HashMap<>();
//     private Map<Long, VoteOption> voteOptions = new HashMap<>();
//     private EntityManagerFactory emf;

//     public PollManager(@Autowired EntityManagerFactory emf) {
//       this.emf = emf;
//     }

//     // Polls
//     public Poll getPoll(Long id) {
//         return polls.get(id);
//     }

    
//     public boolean addPoll(Long id, Poll poll) {
//         if (polls.containsKey(id))
//             return false;
//         polls.put(id, poll);
//         return true;
//     }

//     public Poll updatePoll(Long id, Poll poll) {
//         if (!polls.containsKey(id))
//             return null;
//         polls.put(id, poll);
//         return poll;
//     }

//     public boolean removePoll(Long id) {
//         return polls.remove(id) != null;
//     }

//     public List<Poll> getAllPolls() {
//         return new ArrayList<>(polls.values());
//     }

//     public Instant getValidUntil(Long id) {
//         Poll poll = polls.get(id);
//         return poll != null ? poll.getValidUntil() : null;
//     }

//     public Boolean setValidUntil(Long id, Instant validUntil) {
//         Poll poll = polls.get(id);
//         if (poll == null) {
//             return false;
//         }
//         poll.setValidUntil(validUntil);
//         return true;
//     }

//     // Users
//     public boolean addUser(Long id, User user) {
//         if (users.containsKey(id))
//             return false;
//         users.put(id, user);
//         return true;
//     }

//     public User getUser(Long id) {
//         return users.get(id);
//     }

//     public User updateUser(Long id, User user) {
//         if (!users.containsKey(id))
//             return null;

//         User existingUser = users.get(id);
//         existingUser.setUsername(user.getUsername());
//         existingUser.setEmail(user.getEmail());
//         existingUser.setPassword(user.getPassword());

//         return existingUser;
//     }

//     public boolean removeUser(Long id) {
//         return users.remove(id) != null;
//     }

//     public List<User> getAllUsers() {
//         return new ArrayList<>(users.values());
//     }

//     // Votes
//     public boolean addVote(Long id, Vote vote) {
//         if (votes.containsKey(id))
//             return false;
//         votes.put(id, vote);
//         return true;
//     }

//     public Vote getVote(Long id) {
//         return votes.get(id);
//     }

//     public boolean updateVote(Long id, Vote vote) {
//         if (!votes.containsKey(id))
//             return false;
//         votes.put(id, vote);
//         return true;
//     }

//     public boolean removeVote(Long id) {
//         return votes.remove(id) != null;
//     }

//     public List<Vote> getAllVotes() {
//         return new ArrayList<>(votes.values());
//     }

//     // Vote Options
//     public VoteOption getVoteOption(Long optionId) {
//         return voteOptions.get(optionId);
//     }

//     public boolean addVoteOption(Long id, VoteOption option) {
//         if (voteOptions.containsKey(id))
//             return false;
//         voteOptions.put(id, option);
//         return true;
//     }

//     public boolean updateVoteOption(Long id, VoteOption option) {
//         if (!voteOptions.containsKey(id))
//             return false;
//         voteOptions.put(id, option);
//         return true;
//     }

//     public boolean removeVoteOption(Long id) {
//         return voteOptions.remove(id) != null;
//     }

// }

package com.example.demo.experiment2.managers;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.experiment2.entities.Poll;
import com.example.demo.experiment2.entities.User;
import com.example.demo.experiment2.entities.Vote;
import com.example.demo.experiment2.entities.VoteOption;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

@Component
public class PollManager {

    private final EntityManagerFactory emf;

    public PollManager(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Users 
    public boolean addUser(User user) {
        return persistEntity(user);
    }

    public User getUser(Long id) {
        return findEntity(User.class, id);
    }

    public User updateUser(User user) {
        return mergeEntity(user);
    }

    public boolean removeUser(Long id) {
        return removeEntity(User.class, id);
    }

    public List<User> getAllUsers() {
        return getAllEntities(User.class);
    }

    //  Polls 
    public boolean addPoll(Poll poll) {
        return persistEntity(poll);
    }

    public Poll getPoll(Long id) {
        return findEntity(Poll.class, id);
    }

    public Poll updatePoll(Poll poll) {
        return mergeEntity(poll);
    }

    public boolean removePoll(Long id) {
        return removeEntity(Poll.class, id);
    }

    public List<Poll> getAllPolls() {
        return getAllEntities(Poll.class);
    }

    public boolean setValidUntil(Long id, Instant validUntil) {
        Poll poll = getPoll(id);
        if (poll == null) return false;
        poll.setValidUntil(validUntil);
        mergeEntity(poll);
        return true;
    }

    public Instant getValidUntil(Long id) {
        Poll poll = getPoll(id);
        return poll != null ? poll.getValidUntil() : null;
    }

    //  Votes 
    public boolean addVote(Vote vote) {
        return persistEntity(vote);
    }

    public Vote getVote(Long id) {
        return findEntity(Vote.class, id);
    }

    public Vote updateVote(Vote vote) {
        return mergeEntity(vote);
    }

    public boolean removeVote(Long id) {
        return removeEntity(Vote.class, id);
    }

    public List<Vote> getAllVotes() {
        return getAllEntities(Vote.class);
    }

    //  Vote Options 
    public boolean addVoteOption(VoteOption option) {
        return persistEntity(option);
    }

    public VoteOption getVoteOption(Long id) {
        return findEntity(VoteOption.class, id);
    }

    public VoteOption updateVoteOption(VoteOption option) {
        return mergeEntity(option);
    }

    public boolean removeVoteOption(Long id) {
        return removeEntity(VoteOption.class, id);
    }

    //  Helper methods 
    private <T> boolean persistEntity(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    private <T> T mergeEntity(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T merged = em.merge(entity);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    private <T> T findEntity(Class<T> clazz, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }

    private <T> boolean removeEntity(Class<T> clazz, Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entity = em.find(clazz, id);
            if (entity != null) {
                em.remove(entity);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    private <T> List<T> getAllEntities(Class<T> clazz) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<T> query = em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

