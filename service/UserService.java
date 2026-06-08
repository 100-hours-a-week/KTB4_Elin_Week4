package community.api.service;

import community.api.dto.UserRequestDto;
import community.api.dto.UserResponseDto;
import community.api.entity.User;
import community.api.exception.NotFoundException;
import community.api.exception.UnauthorizedException;
import community.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto.Register register(UserRequestDto.Register request) {
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage()
        );

        User savedUser = userRepository.save(user);

        return UserResponseDto.Register.from(savedUser);
    }

    public UserResponseDto.Login login(UserRequestDto.Login request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new UnauthorizedException("login_failed");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException("login_failed");
        }

        return UserResponseDto.Login.from(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId);

        if (user == null) {
            throw new NotFoundException("user_not_found");
        }

        userRepository.deleteById(userId);
    }
}