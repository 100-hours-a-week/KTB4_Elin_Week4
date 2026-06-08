package community.api.entity;

import lombok.Getter;

@Getter
public class Like {

    private Long postId;
    private Long userId;

    public Like(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }
}