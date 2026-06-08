package community.api.entity;

import lombok.Getter;

@Getter
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String contentImage;

    public Post(Long userId, String title, String content, String contentImage) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.contentImage = contentImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void update(String title, String content, String contentImage) {
        this.title = title;
        this.content = content;
        this.contentImage = contentImage;
    }
}