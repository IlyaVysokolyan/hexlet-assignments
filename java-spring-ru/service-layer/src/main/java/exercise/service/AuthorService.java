package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;

    //Получение списка авторов
    public List<AuthorDTO> gerAll() {
        var all = authorRepository.findAll();
        var result = all.stream().map(authorMapper::map).toList();
        return result;
    }

    //Получение автора по id
    public AuthorDTO authorById(Long id) {
        var authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        var authorDto = authorMapper.map(authorEntity);
        return authorDto;
    }

    //Добавление нового автора
    public AuthorDTO create(AuthorCreateDTO authorCreateDTO) {
        var author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);
        var authorDto = authorMapper.map(author);
        return authorDto;
    }

    //Редактирование автора
    public AuthorDTO update(AuthorUpdateDTO authorUpdateDTO, Long id) {
        var entityAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        authorMapper.update(authorUpdateDTO, entityAuthor);
        authorRepository.save(entityAuthor);
        var authorDto = authorMapper.map(entityAuthor);
        return authorDto;
    }

    public void delete(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        authorRepository.delete(author);
    }

    //


    // END
}
