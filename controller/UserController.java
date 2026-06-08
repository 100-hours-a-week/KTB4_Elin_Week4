package community.api.controller;

import community.api.dto.UserRequestDto;
import community.api.dto.UserResponseDto;
import community.api.response.ApiResponse;
import community.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<UserResponseDto.Register>> register(
            @Valid @RequestBody UserRequestDto.Register request
    ) {
        UserResponseDto.Register response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of("register_success", response));
    }

    @PostMapping("/users/sessions")
    public ResponseEntity<ApiResponse<UserResponseDto.Login>> login(
            @Valid @RequestBody UserRequestDto.Login request
    ) {
        UserResponseDto.Login response = userService.login(request);

        return ResponseEntity.ok(
                ApiResponse.of("login_success", response)
        );
    }

    @DeleteMapping("/users/profile")
    public ResponseEntity<Void> deleteUser(
            @RequestHeader("X-USER-ID") Long userId
    ) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}