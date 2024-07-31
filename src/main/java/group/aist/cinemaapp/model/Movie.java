package group.aist.cinemaapp.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "movies")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "movieWithLanguages",
        attributeNodes = {
                @NamedAttributeNode("languages"),
                @NamedAttributeNode("subtitleLanguages")
        }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    String name;
    String image;
    String description;
    LocalDateTime releaseTime;
    Integer duration;
    Integer ageLimit;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    @ToString.Exclude
    Set<MovieLanguage> languages;

    @ManyToMany
    @JoinTable(
            name = "movie_subtitle_languages",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    @ToString.Exclude
    @JsonManagedReference
    Set<Language> subtitleLanguages;


    Integer status;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
