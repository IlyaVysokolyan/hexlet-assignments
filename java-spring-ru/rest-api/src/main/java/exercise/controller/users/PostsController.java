package exercise.controller.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {

    @GetMapping("/users/{id}/posts")
    public List<Post> show(@PathVariable("id") int id) {
        return Data.getPosts().stream()
                .filter(s->s.getUserId()==id)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable("id") int id,
                       @RequestBody Post post) {
        post.setUserId(id);

        return post;
    }
}

// END
