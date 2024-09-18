package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import exercise.model.Post;
import exercise.repository.PostRepository;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public List<Post> index() {
        return postRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        postRepository.save(post);
        return post;
    }

    @GetMapping("/{id}")
    public Post show(@PathVariable long id) {
        return postRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable long id, @RequestBody Post data) {
        var post = postRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Post with id " + id + " not found"));
        post.setTitle(data.getTitle());
        post.setBody(data.getBody());
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable long id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}
// END
