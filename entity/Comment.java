package community.api.entity;

import lombok.Getter;

@Getter
public class Comment {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;

    public Comment(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(String content) {
        this.content = content;
    }
}