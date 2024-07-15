package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<PostDTO> index() {
        List<PostDTO> listPostDTO = new ArrayList<>();
        var posts = postRepository.findAll();

        for (Post post : posts) {
            var commntsListOld = commentRepository.findByPostId(post.getId());
            var res = commntsListOld.stream().map(this::toCommentDTO).toList();
            listPostDTO.add(toPostDTO(post, res));
        }

        return listPostDTO;
    }

    @GetMapping("/{id}")
    public PostDTO show(@PathVariable long id) {
        return index().stream().filter(s -> s.getId() == id).findAny().orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }


    private PostDTO toPostDTO(Post post, List<CommentDTO> commentDTOList) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(commentDTOList);

        return dto;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }


}
// END
