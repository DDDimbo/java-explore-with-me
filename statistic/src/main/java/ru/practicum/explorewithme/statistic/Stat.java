package ru.practicum.explorewithme.statistic;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "statistics")
public class Stat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String app;

    @NotNull
    private String uri;

    @NotNull
    private String ip;

    @NotNull
    private LocalDateTime timestamp;

    public Stat() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stat stat = (Stat) o;
        return Objects.equals(id, stat.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
