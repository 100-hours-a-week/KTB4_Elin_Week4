package community.api.dto;

import community.api.entity.User;
import lombok.Getter;

public class UserResponseDto {

    @Getter
    public static class Register {
        private Long userId;
        private String email;
        private String nickname;
        private String profileImage;

        public Register(Long userId, String email, String nickname, String profileImage) {
            this.userId = userId;
            this.email = email;
            this.nickname = nickname;
            this.profileImage = profileImage;
        }

        public static Register from(User user) {
            return new Register(
                    user.getId(),
                    user.getEmail(),
                    user.getNickname(),
                    user.getProfileImage()
            );
        }
    }

    @Getter
    public static class Login {

        private Long userId;

        public Login(Long userId) {
            this.userId = userId;
        }

        public static Login from(User user) {
            return new Login(user.getId());
        }
    }
}