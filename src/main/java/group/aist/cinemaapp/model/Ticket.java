package group.aist.cinemaapp.model;

import java.math.BigDecimal;
import java.util.Objects;




import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="tickets")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "ticketWithRelations",
        attributeNodes = {
                @NamedAttributeNode("seat"),


        }
)
public class Ticket {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    BigDecimal price;
    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
    Integer status;


    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "hall_id")
    @ToString.Exclude
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(getId(), sector.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}



