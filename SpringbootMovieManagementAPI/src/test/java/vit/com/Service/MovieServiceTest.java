package vit.com.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import vit.com.Entity.Movies;
import vit.com.Exception.ResourceNotFoundException;
import vit.com.Repository.MovieRepository;

class MovieServiceTest {

    @Mock
    private MovieRepository repo;

    @InjectMocks
    private MovieService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Movies movie = new Movies();
        movie.setTitle("Inception");

        when(repo.findAll()).thenReturn(Arrays.asList(movie));

        List<Movies> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void testGetByIdFound() {
        Movies movie = new Movies();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(repo.findById(1L)).thenReturn(Optional.of(movie));

        Movies result = service.getById(1L);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    void testGetByIdNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void testCreate() {
        Movies movie = new Movies();
        movie.setTitle("Interstellar");

        when(repo.save(movie)).thenReturn(movie);

        Movies result = service.create(movie);

        assertEquals("Interstellar", result.getTitle());
    }

    @Test
    void testUpdate() {
        Movies existing = new Movies();
        existing.setId(1L);
        existing.setTitle("Old Title");

        Movies updated = new Movies();
        updated.setTitle("New Title");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any(Movies.class))).thenAnswer(i -> i.getArgument(0));

        Movies result = service.update(1L, updated);

        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testDelete() {
        doNothing().when(repo).deleteById(1L);

        service.delete(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
