package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

// BEGIN
@Entity
@Table(name = "task")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private int id;
    private String title;
    private String description; // описание задачи
    @CreatedDate
    private LocalDate createdAt; // дата создания новой задачи
    @LastModifiedDate
    private LocalDate updatedAt; // дата последнего обновления задачи

}
// END
