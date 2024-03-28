package exercise.controller.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
public class PostsController {


    @GetMapping("/api/users/{id}/posts")
    public List<Post> show(@PathVariable String id) {
        List<Post> newListPost = Data.getPosts();
        List<Post> posts = new ArrayList<>();
        for (Post post : newListPost) {
            if (post.getUserId() == Integer.parseInt(id)) {
                posts.add(post);
            }
        }
        return posts;
    }

    @PostMapping("/api/users/{id}/posts")
    public ResponseEntity<Post> create(@RequestBody Post post, @PathVariable String id) {
        Post post1 = post;
        post1.setUserId(Integer.parseInt(id));
        post1.setSlug(post.getSlug());
        post1.setTitle(post.getTitle());
        post1.setBody(post.getBody());
        return ResponseEntity.status(HttpStatus.CREATED).body(post1);
    }
}

// END
