package exercise.service;

import exercise.dto.*;
import exercise.exception.BadRequestException;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private AuthorRepository authorRepository;

    //Получение списка книг
    public List<BookDTO> gerAll() {
        var all = bookRepository.findAll();
        var result = all.stream().map(bookMapper::map).toList();
        return result;
    }

    //Получение книги по id
    public BookDTO authorById(Long id) {
        var bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        var bookDto = bookMapper.map(bookEntity);
        return bookDto;
    }

    //Добавление новой книги
    public BookDTO create(BookCreateDTO bookCreateDTO) {
        var bad = authorRepository.findById(bookCreateDTO.getAuthorId())
                .orElseThrow(() -> new BadRequestException("BadRequestException"));
        var book = bookMapper.map(bookCreateDTO);
        bookRepository.save(book);
        var bookDto = bookMapper.map(book);
        return bookDto;
    }

    //Редактирование книги
    public BookDTO update(BookUpdateDTO bookUpdateDTO, Long id) {
        var bad = authorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("BadRequestException"));
        var entityBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        bookMapper.update(bookUpdateDTO, entityBook);
        bookRepository.save(entityBook);
        var bookDto = bookMapper.map(entityBook);
        return bookDto;
    }

    public void delete(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResourceNotFoundException"));
        bookRepository.delete(book);
    }
    // END
}
