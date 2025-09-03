package vit.com.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vit.com.Entity.Movies;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    @Test
    void testSaveMovie() {
        Movies movie = new Movies(null, "Inception", "Christopher Nolan", 2010, "Sci-Fi", 9.0);
        Movies saved = repository.save(movie);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Inception");
    }

    @Test
    void testFindById() {
        Movies movie = new Movies(null, "Interstellar", "Christopher Nolan", 2014, "Sci-Fi", 8.6);
        Movies saved = repository.save(movie);

        Optional<Movies> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getDirector()).isEqualTo("Christopher Nolan");
    }

    @Test
    void testFindAll() {
        Movies m1 = new Movies(null, "Dunkirk", "Christopher Nolan", 2017, "War", 8.0);
        Movies m2 = new Movies(null, "Tenet", "Christopher Nolan", 2020, "Action", 7.5);

        repository.save(m1);
        repository.save(m2);

        List<Movies> movies = repository.findAll();

        assertThat(movies).hasSize(2);
    }

    @Test
    void testDeleteById() {
        Movies movie = new Movies(null, "The Dark Knight", "Christopher Nolan", 2008, "Action", 9.1);
        Movies saved = repository.save(movie);

        repository.deleteById(saved.getId());

        Optional<Movies> found = repository.findById(saved.getId());

        assertThat(found).isEmpty();
    }
}
