package com.jparest.main;

import com.jparest.main.Animal;
import java.util.List;
import java.util.Optional;





import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;


// All query method resources are exposed under the resource search
// Ej: http://localhost:8080/animals/search/findByName

@Repository
//@RepositoryRestResource(collectionResourceRel = "animals", path = "animals")
public interface AnimalRepository extends PagingAndSortingRepository<Animal, Long> {

    // Por defecto los métodos de un ainterfaz son públicos
    
    
        @RestResource(path = "breeds") // El path va en plural.
	List<Animal> findByBreed(@Param("breed") String breed);
        
        
        @RestResource(path = "names", rel="names") // El path va en plural.
        // Ej: http://localhost:8181/animals/search/names
       List<Animal> findByName(@Param("name") String name);
       
       @RestResource(path="ids", rel="ids")
       Optional<Animal> findByIdAnimal(@Param("id") String id);
       
       @RestResource(path="owners",rel="owners")
       List<Animal> findByOwnersIdPerson(@Param("idPerson") String idPerson);
       //SELECT b from Animal b WHERE b.owners.IdPerson = :idPerson
       
       @RestResource(path="names_breeds", rel="names_breeds")
       List<Animal> findByNameAndBreed(@Param("name") String name, @Param("breed") String breed);        
       
       
       
        
       
       
        
/*          
At runtime, Spring Data REST will create an implementation of this interface automatically. 
Then it will use the @RepositoryRestResource annotation to direct Spring MVC to create 
RESTful endpoints at /animals.       
*/       
        
        
/* Ejemplos de consultas:

$ curl http://localhost:8080
$ curl http://localhost:8080/animals
$ curl -i -X POST -H "Content-Type:application/json" -d '{  "firstName" : "Frodo",  "lastName" : "Baggins" }' http://localhost:8080/animals
$ curl http://localhost:8080/animals/6
$ curl http://localhost:8080/animals/search
$ curl http://localhost:8080/animals/search/findByLastName?name=Baggins
$ curl -X PUT -H "Content-Type:application/json" -d '{ "firstName": "Bilbo", "lastName": "Baggins" }' http://localhost:8080/animals/6
$ curl -X PATCH -H "Content-Type:application/json" -d '{ "firstName": "Bilbo Jr." }' http://localhost:8080/animals/6
$ curl -X DELETE http://localhost:8080/animals/6



*/
        

}
