package community.api.service;

import community.api.dto.LikeResponseDto;
import community.api.entity.Like;
import community.api.entity.Post;
import community.api.exception.ConflictException;
import community.api.exception.NotFoundException;
import community.api.repository.LikeRepository;
import community.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeResponseDto addLike(Long userId, Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new ConflictException("already_liked"); // 좋아요 누른 게시글에 또 누를 경우
        }

        likeRepository.save(new Like(postId, userId));

        int likeCount = likeRepository.countByPostId(postId);

        return new LikeResponseDto(postId, userId, likeCount);
    }

    public void deleteLike(Long userId, Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        if (!likeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new NotFoundException("like_delete_not_found");
        }

        likeRepository.deleteByPostIdAndUserId(postId, userId);
    }
}