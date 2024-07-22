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
@Table(name = "seats")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "seatWithRelations",
        attributeNodes = {
                @NamedAttributeNode("sector"),
        }
)
public class Seat {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String row;
    Long seatNumber;
    Integer status;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    @ToString.Exclude
    Sector sector;

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
