package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hall")
@Builder
@FieldDefaults(level = PRIVATE)
public class Hall {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    Integer totalSeatsCount;
    Integer status;
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
