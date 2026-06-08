package community.api.repository;

import community.api.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private Long sequence = 1L;

    public User save(User user) {
        user.setId(sequence);
        users.put(sequence, user);
        sequence++;

        return user;
    }

    public User findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public User findById(Long userId) {
        return users.get(userId);
    }

    public void deleteById(Long userId) {
        users.remove(userId);
    }
}