package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    //Вывод список постов
    @GetMapping("/posts")
    public List<Post> show() {
        return posts;
    }

    @GetMapping("/posts?page=2&limit=10")
    public List<Post> postListPage(@RequestParam String page, @RequestParam Integer limit) {
        return posts.stream().limit(limit).toList();
    }

    //Поиск поста по его id
    @GetMapping("/posts/{id}")
    public Optional<Post> index(@PathVariable String id) {
        var post = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return post;
    }

    //Создание нового поста
    @PostMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);
        return post;
    }

    //Обновление поста
    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post post) {
        var nPage = posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if (nPage.isPresent()) {
            var value = nPage.get();
            value.setId(post.getId());
            value.setBody(post.getBody());
            value.setTitle(post.getTitle());
        }
        return post;
    }

    //Удаление поста
    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
