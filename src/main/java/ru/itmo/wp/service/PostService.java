package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCommentForm;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment(PostCommentForm postCommentForm, User user) {
        Comment comment = new Comment();
        comment.setText(postCommentForm.getText().trim());
        long postId = postCommentForm.getPostId();
        postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            comment.setUser(user);
            post.addComment(comment);
            postRepository.save(post);
            return null;
        });
    }
}
