package vit.com.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "moviedb")
@NoArgsConstructor
@AllArgsConstructor
@Data


public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Version
    private Long id;

    @NotBlank
    private String title;

    private String director;
    private Integer releaseYear;
    private String genre;

 
    @Min(1)
    @Max(10)
    private Double rating;
}
