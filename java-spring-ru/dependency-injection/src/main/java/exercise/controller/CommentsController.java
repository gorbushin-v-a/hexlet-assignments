package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> index() {
        return commentRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    @GetMapping("/{id}")
    public Comment show(@PathVariable long id) {
        return commentRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Comment with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment data) {
        var comment = commentRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Comment with id " + id + " not found"));
        comment.setPostId(data.getPostId());
        comment.setBody(data.getBody());
        return data;
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable long id) {
        commentRepository.deleteById(id);
    }
}
// END
