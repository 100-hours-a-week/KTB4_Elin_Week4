package community.api.dto;

import community.api.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private String contentImage;

    public PostResponseDto(Long postId, Long userId, String title, String content, String contentImage) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.contentImage = contentImage;
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getUserId(),
                post.getTitle(),
                post.getContent(),
                post.getContentImage()
        );
    }
}