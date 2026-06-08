package community.api.service;

import community.api.dto.PostRequestDto;
import community.api.dto.PostResponseDto;
import community.api.entity.Post;
import community.api.exception.ForbiddenException;
import community.api.exception.NotFoundException;
import community.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto createPost(Long userId, PostRequestDto request) {
        Post post = new Post(
                userId,
                request.getTitle(),
                request.getContent(),
                request.getContentImage()
        );

        Post savedPost = postRepository.save(post);

        return PostResponseDto.from(savedPost);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostResponseDto::from)
                .toList();
    }

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        return PostResponseDto.from(post);
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

        return PostResponseDto.from(post);
    }

    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_delete_not_found");
        }

        if (!post.getUserId().equals(userId)) {
            throw new ForbiddenException("forbidden_error");
        }

        postRepository.deleteById(postId);
    }
}