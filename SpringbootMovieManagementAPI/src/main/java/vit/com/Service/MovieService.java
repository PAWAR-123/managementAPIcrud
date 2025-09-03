package vit.com.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vit.com.Entity.Movies;
import vit.com.Exception.ResourceNotFoundException;
import vit.com.Repository.MovieRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repo;

    public List<Movies> getAll() { return repo.findAll(); }
    public Movies getById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)); }
    public Movies create(Movies movie) { return repo.save(movie); }
    public Movies update(Long id, Movies updated) {
        Movies existing = getById(id);
        BeanUtils.copyProperties(updated, existing, "id");
        return repo.save(existing);
    }
    public void delete(Long id) { repo.deleteById(id); }
}
