package community.api.repository;

import community.api.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {

    private final Map<Long, Comment> comments = new HashMap<>();
    private Long sequence = 1L;

    public Comment save(Comment comment) {
        comment.setId(sequence);
        comments.put(sequence, comment);
        sequence++;

        return comment;
    }

    public Comment findById(Long commentId) {
        return comments.get(commentId);
    }

    public List<Comment> findAllByPostId(Long postId) {
        return comments.values()
                .stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .toList();
    }
    public int countByPostId(Long postId) {
        return findAllByPostId(postId).size();
    }

    public void deleteById(Long commentId) {
        comments.remove(commentId);
    }
    public void deleteByPostId(Long postId) {
        comments.values().removeIf(comment -> comment.getPostId().equals(postId));
    }

}