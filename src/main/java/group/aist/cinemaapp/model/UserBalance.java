package group.aist.cinemaapp.model;

import group.aist.cinemaapp.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user_balance")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "balanceWithRelations",
        attributeNodes = {
                @NamedAttributeNode("user"),

        }
)
public class UserBalance {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Enumerated(STRING)
    CurrencyType currency;

    BigDecimal amount;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "user_id")
    User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalance userBalance = (UserBalance) o;
        return Objects.equals(getId(), userBalance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
