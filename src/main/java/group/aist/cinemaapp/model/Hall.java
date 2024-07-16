package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "halls")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "hallWithRelations",
        attributeNodes = {
                @NamedAttributeNode("sector"),
        }
)
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

    @OneToMany(mappedBy = "hall")
    Set<Sector> sector;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return Objects.equals(getId(), hall.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
