package co.edu.unisabana.Gribu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @URL
    private String url_downloadable;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LessonUser> lessonUsers;

    @ManyToMany
    @JoinTable(
            name = "lesson_label", joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "label_id",referencedColumnName = "id")
    )

    private List<Label> labels;
    @ManyToMany
    @JoinTable(
            name = "lesson_route", joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "route_id",referencedColumnName = "id")
    )
    private List<Route> routes;




}
