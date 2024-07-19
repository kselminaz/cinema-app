package group.aist.cinemaapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_languages")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "movieLanguageWithRelations",
        attributeNodes = {
                @NamedAttributeNode("movie"),
                @NamedAttributeNode("language")
        }
)
public class MovieLanguage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "movie_id")
    Movie movie;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "language_id")
    Language language;

    Boolean isMain;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieLanguage that = (MovieLanguage) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
