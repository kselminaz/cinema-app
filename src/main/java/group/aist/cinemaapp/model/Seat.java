package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.EAGER;
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
public class Seat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String row;
    Integer seat_number;
    Integer status;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "sector_id")
    @ToString.Exclude
    Sector sector;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;
}
