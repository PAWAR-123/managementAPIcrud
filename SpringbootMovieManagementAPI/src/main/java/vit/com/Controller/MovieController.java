package vit.com.Controller;

import java.util.List;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vit.com.Entity.Movies;
import vit.com.Service.MovieService;


@RestController
@RequestMapping("/movies")
//@Tag(name = "Movie API", description = "CRUD operations for movies")
public class MovieController {
    @Autowired
    private MovieService service;

    @GetMapping
    public List<Movies> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Movies getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping()
    public Movies create(@Valid @RequestBody Movies movie) { return service.create(movie); }

    @PutMapping("/{id}")
    public Movies update(@PathVariable Long id, @Valid @RequestBody Movies movie) {
        return service.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
