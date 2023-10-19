package tana.todo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tana.todo.enums.Status;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private ZonedDateTime due_datetime;
    @Column(insertable = false, updatable = false)
    private ZonedDateTime created_at;
    @Column(insertable = false, updatable = false)
    private ZonedDateTime updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
