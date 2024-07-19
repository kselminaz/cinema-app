package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tickets")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "userTicketWithRelations",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("ticket"),
        }
)
public class UserTicket {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String ticketNumber;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "ticket_id")
    Ticket ticket;

    Integer status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTicket userTicket = (UserTicket) o;
        return Objects.equals(getId(), userTicket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
