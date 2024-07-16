package group.aist.cinemaapp.model;


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
@Table(name = "movie_sessions")
@Builder
@FieldDefaults(level = PRIVATE)
@NamedEntityGraph(
        name = "movieSessionWithRelations",
        attributeNodes = {
                @NamedAttributeNode("movie"),
                @NamedAttributeNode("hall"),
                @NamedAttributeNode("language"),
                @NamedAttributeNode("subtitleLanguage"),

        }
)
public class MovieSession {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "movie_id")
    @ToString.Exclude
    Movie movie;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "hall_id")
    @ToString.Exclude
    Hall hall;


    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "language_id")
    @ToString.Exclude
    MovieLanguage language;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "subtitle_language_id")
    @ToString.Exclude
    Language subtitleLanguage;

    LocalDateTime datetime;

    Integer status;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieSession that = (MovieSession) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

