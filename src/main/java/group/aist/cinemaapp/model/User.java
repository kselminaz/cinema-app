package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "userWithRelations",
        attributeNodes = {
                @NamedAttributeNode("balances"),

        }
)
public class User {
    @Id
    String id;

    String username;

    String fullName;

    String phone;

    String mail;

    LocalDate dob;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    Set<UserBalance> balances;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
