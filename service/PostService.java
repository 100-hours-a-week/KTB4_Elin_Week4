package community.api.service;

import community.api.dto.PostRequestDto;
import community.api.dto.PostResponseDto;
import community.api.entity.Post;
import community.api.entity.User;
import community.api.exception.ForbiddenException;
import community.api.exception.NotFoundException;
import community.api.exception.UnauthorizedException;
import community.api.repository.CommentRepository;
import community.api.repository.LikeRepository;
import community.api.repository.PostRepository;
import community.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        Post post = new Post(
                userId,
                request.getTitle(),
                request.getContent(),
                request.getContentImage()
        );

        Post savedPost = postRepository.save(post);

        return toResponseDto(savedPost);
    }

    public List<PostResponseDto> getPosts(Long userId) {
        if (userId == null) {
            throw new UnauthorizedException("unauthorized_error");
        }
        return postRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        post.increaseViewCount();
        return toResponseDto(post);
    }

    public PostResponseDto updatePost(Long userId, Long postId, PostRequestDto request) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        if (!post.getUserId().equals(userId)) {
            throw new ForbiddenException("forbidden_error");
        }

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getContentImage()
        );

        return toResponseDto(post);
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        if (!post.getUserId().equals(userId)) {
            throw new ForbiddenException("forbidden_error");
        }

        commentRepository.deleteByPostId(postId);
        likeRepository.deleteByPostId(postId);
        postRepository.deleteById(postId);
    }

    private PostResponseDto toResponseDto(Post post) {
        User user = userRepository.findById(post.getUserId());

        if (user == null) {
            throw new NotFoundException("user_not_found");
        }

        int likeCount = likeRepository.countByPostId(post.getId());
        int commentCount = commentRepository.countByPostId(post.getId());

        return PostResponseDto.from(post, user, likeCount, commentCount);
    }
}