package community.api.entity;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    public User(String email, String password, String nickname, String profileImage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
