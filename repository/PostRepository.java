package community.api.repository;

import community.api.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private final Map<Long, Post> posts = new HashMap<>();
    private Long sequence = 1L;

    public Post save(Post post) {
        post.setId(sequence);
        posts.put(sequence, post);
        sequence++;

        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Post findById(Long postId) {
        return posts.get(postId);
    }

    public void deleteById(Long postId) {
        posts.remove(postId);
    }

}