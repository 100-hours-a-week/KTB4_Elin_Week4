package community.api.service;

import community.api.dto.CommentRequestDto;
import community.api.dto.CommentResponseDto;
import community.api.entity.Comment;
import community.api.entity.Post;
import community.api.exception.ForbiddenException;
import community.api.exception.NotFoundException;
import community.api.repository.CommentRepository;
import community.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(
            Long userId,
            Long postId,
            CommentRequestDto request
    ) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        Comment comment = new Comment(
                postId,
                userId,
                request.getContent()
        );

        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.from(savedComment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    public CommentResponseDto updateComment(
            Long userId,
            Long postId,
            Long commentId,
            CommentRequestDto request
    ) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("post_not_found");
        }

        Comment comment = commentRepository.findById(commentId);

        if (comment == null || !comment.getPostId().equals(postId)) {
            throw new NotFoundException("comment_not_found");
        }

        if (!comment.getUserId().equals(userId)) {
            throw new ForbiddenException("forbidden_error");
        }

        comment.update(request.getContent());

        return CommentResponseDto.from(comment);
    }

    public void deleteComment(Long userId, Long postId, Long commentId) {
        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new NotFoundException("comment_not_found");
        }

        Comment comment = commentRepository.findById(commentId);

        if (comment == null || !comment.getPostId().equals(postId)) {
            throw new NotFoundException("comment_not_found");
        }

        if (!comment.getUserId().equals(userId)) {
            throw new ForbiddenException("forbidden_error");
        }

        commentRepository.deleteById(commentId);
    }
}