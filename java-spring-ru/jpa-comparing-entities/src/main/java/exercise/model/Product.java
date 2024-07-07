package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
@Entity
@Table(name = "product")
@EqualsAndHashCode(of = {"title","price"})
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private int price;
}
// END
