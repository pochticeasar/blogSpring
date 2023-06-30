package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCommentForm;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }


    @Guest
    @GetMapping("/post/{id}")
    public String post(Model model, @PathVariable String id, HttpSession httpSession) {
        try {
            Post post = postService.findById(Long.parseLong(id));
            if (post == null) {
                putMessage(httpSession, "This post does not exist");
                return "redirect:/";
            }
            model.addAttribute("commentForm", new PostCommentForm());
            model.addAttribute("post", post);
            model.addAttribute("comments", post.getComments());
        } catch (NumberFormatException ignored) {
        }
        return "PostPage";
    }

    @PostMapping("/post/writeComment")
    private String writeComment(@Valid @ModelAttribute("commentForm") PostCommentForm postCommentForm,
                                BindingResult bindingResult, Model model, HttpSession httpSession) {
        Post post = postService.findById(postCommentForm.getPostId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            model.addAttribute("comments", postService.findById(postCommentForm.getPostId()).getComments());
            return "PostPage";
        }
        User user = getUser(httpSession);
        postService.writeComment(postCommentForm, user);
        return "redirect:/post/" + post.getId();
    }
}
