package vit.com.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vit.com.Entity.Movies;

public interface MovieRepository extends JpaRepository<Movies, Long> {
	
	
	
	
}