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
@Table(name = "sectors")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(name = "sectorWithRelations",
        attributeNodes = {
                @NamedAttributeNode("hall"),
                @NamedAttributeNode("seat")
        }
)
public class Sector {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    Integer status;
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    @ToString.Exclude
    Hall hall;

    @OneToMany(mappedBy = "sector")
    Set<Seat> seat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sector sector)) return false;
        return Objects.equals(getId(), sector.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
