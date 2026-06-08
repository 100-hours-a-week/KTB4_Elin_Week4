package community.api.repository;

import community.api.entity.Like;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LikeRepository {

    private final List<Like> likes = new ArrayList<>();

    public boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return likes.stream()
                .anyMatch(like ->
                        like.getPostId().equals(postId)
                                && like.getUserId().equals(userId)
                );
    }

    public void save(Like like) {
        likes.add(like);
    }

    public void deleteByPostIdAndUserId(Long postId, Long userId) {
        likes.removeIf(like ->
                like.getPostId().equals(postId)
                        && like.getUserId().equals(userId)
        );
    }

    public int countByPostId(Long postId) {
        return (int) likes.stream()
                .filter(like -> like.getPostId().equals(postId))
                .count();
    }
}