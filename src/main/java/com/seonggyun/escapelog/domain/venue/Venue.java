package com.seonggyun.escapelog.domain.venue;

import com.seonggyun.escapelog.domain.theme.Theme;
import com.seonggyun.escapelog.domain.venue.exception.VenueErrorCode;
import com.seonggyun.escapelog.domain.venue.exception.VenueException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Venue {

    private static final int VENUE_NAME_MAX_LENGTH = 100;
    private static final int REGION_MAX_LENGTH = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_id")
    private Long id;

    private String name;
    private String region;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Theme> themes = new ArrayList<>();

    public Venue(String name, String region) {
        this.name = name;
        this.region = region;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new VenueException(VenueErrorCode.VENUE_NAME_EMPTY);
        }
        if (name.length() > VENUE_NAME_MAX_LENGTH) {
            throw new VenueException(VenueErrorCode.VENUE_NAME_TOO_LONG);
        }

        if (region != null && region.length() > REGION_MAX_LENGTH) {
            throw new VenueException(VenueErrorCode.REGION_TOO_LONG);
        }
    }

    public void addTheme(Theme theme) {
        if (theme == null) {
            throw new VenueException(VenueErrorCode.THEME_NULL);
        }
        if (themes.contains(theme)) {
            throw new VenueException(VenueErrorCode.THEME_DUPLICATE);
        }
        themes.add(theme);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venue)) {
            return false;
        }
        Venue venue = (Venue) o;
        return Objects.equals(id, venue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
