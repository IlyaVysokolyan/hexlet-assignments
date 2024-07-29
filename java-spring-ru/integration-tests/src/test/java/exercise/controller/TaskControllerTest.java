package exercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.transaction.annotation.Transactional;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        /*testTask = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.book().title())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().sentence())
                .create();*/
        testTask = Instancio.of(Task.class).create();
    }


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testShow() throws Exception {
        mockMvc.perform(get("/task/{id}", testTask.getId()))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testCreate() throws Exception {
        var dto = new Task();
        dto.setTitle(testTask.getTitle());
        dto.setDescription(testTask.getDescription());

        var request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var task = taskRepository.findByTitle(testTask.getTitle()).get();

        assertThat(task).isNotNull();
        assertThat(task.getTitle()).isEqualTo(testTask.getTitle());
        assertThat(task.getDescription()).isEqualTo(testTask.getDescription());
    }

    @Test
    public void testUpdate() throws Exception {
        taskRepository.save(testTask);
        var data = new Task();
        data.setTitle("new title");
        data.setDescription("new description");

        var request = put("/tasks/{id}", testTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var task = taskRepository.findById(testTask.getId()).get();

        assertThat(task.getTitle()).isEqualTo(data.getTitle());
        assertThat(task.getDescription()).isEqualTo(data.getDescription());

    }

    @Test
    public void testDestroy() throws Exception {
        taskRepository.save(testTask);
        var request = delete("/tasks/{id}", testTask.getId());
        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        assertThat(taskRepository.existsById(testTask.getId())).isFalse();
    }

    // END
}
