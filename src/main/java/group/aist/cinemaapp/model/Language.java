package group.aist.cinemaapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "languages")
@Builder
@FieldDefaults(level = PRIVATE)
public class Language {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String isoCode;
    String title;
    Integer status;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "language", orphanRemoval = true)
    @ToString.Exclude
    Set<MovieLanguage> languages;

    @ManyToMany(mappedBy = "subtitleLanguages", fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    @JsonBackReference
    List<Movie> movieWithSubtitleLanguages;

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
