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
@Table(name = "company_info")
@Builder
@FieldDefaults(level = PRIVATE)
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    String aboutText;
    String logo;
    String costumersInformationText;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;


}
