package exercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN
    @Column
    @NotNull
    private String name;
    @Column
    @Email
    private String email;
    @Column
    @Pattern(regexp = "^\\+[0-9]{11,13}$")
    private String phoneNumber;
    @Column
    @Size(min = 4,max = 4)
    private String clubCard;
    @Column
    @Future
    private LocalDate cardValidUntil;
    // END

    @CreatedDate
    private LocalDate createdAt;
}
