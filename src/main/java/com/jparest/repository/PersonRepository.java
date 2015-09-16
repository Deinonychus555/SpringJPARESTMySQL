package com.jparest.repository;

import com.jparest.domain.Person;
import com.jparest.domain.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


// All query method resources are exposed under the resource search
// Ej: http://localhost:8181/people/search/findByLastName
@Repository
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

        @RestResource(path = "lastNames") // El path va en plural.
	List<Person> findByLastNameOrderByFirstNameAsc(@Param("lastName") String lastName);
        
        
        @RestResource(path = "names", rel="names") // El path va en plural.
        // Ej: http://localhost:8181/people/search/names
        List<Person> findByFirstNameIgnoreCase(@Param("firstName") String firstName);
        
        @RestResource(path="optionalName", rel="optionlName")
        Optional<Person> findByFirstName(@Param("name") String name);
        
        @RestResource(path="ids", rel="ids")
        Optional<Person> findByIdPerson(@Param("id") String id);
        
        
        
        @RestResource(path="pets", rel="pets")
        List<Person> findByPetsIdAnimal(@Param("idAnimal") String idAnimal);
        //SELECT b from Person b WHERE b.pets.IdAnimal = :idAnimal
        
        @RestResource(path="pets_age", rel="pets_age")
        List<Person> findByPetsAge(@Param("age") int age);
        
        @RestResource(path="pets_breed", rel="pets_breed")
        List<Person> findByPetsBreed(@Param("breed") String breed);
        
        @RestResource(path="firstNames_lastNames", rel="firstNames_lastNames")
        List<Person> findByFirstNameAndLastNameAllIgnoreCase(@Param("firstName") String firstName, @Param("lastName") String lastName);
        
        /** Te devuelve aquellas personas cuyo nombre y apellido coincidan con el dado */
        @RestResource(path="firstNames_petNames", rel="firstNames_petNames")
        List<Person> findByFirstNameAndPetsName(@Param("firstName") String firstName, @Param("name") String name);
       
         List<Person> findByFirstNameAndAge(@Param("firstName")String firstName, @Param("age") int age);
         
         @Query("select u from Person u where u.age < ?1")
         List<Person> findByYears(int years);
         
         @Query("select u from Person u where u.age > ?1 and u.age < ?2")
         List<Person> findByYears2(int years1, int years2);
         
         
         // http://localhost:8080/people/search/findByYears3?age=27
          @Query("select u from Person u where u.age = :age")
         List<Person> findByYears3(@Param("age") int years);
         
         
         
         
         
         // !! NO FUNCIONA porque si en la query especificas que se usa el parámetro 2 implica que tb
         //tiene que usarse el parámetro 1
         @Query("select u from Person u where u.age  < ?2")
         List<Person> findByYears4(int years1, int years2);
         
         // !! NO FUNCIONA
         @RestResource(path="ages/{age}", rel="ages/{age}")
         List<Person> findByAge(@PathVariable int age);
        
        
        
        
/*
At runtime, Spring Data REST will create an implementation of this interface automatically. 
Then it will use the @RepositoryRestResource annotation to direct Spring MVC to create 
RESTful endpoints at /people.       
*/       
        
        
/* Ejemplos de consultas:

$ curl http://localhost:8080
$ curl http://localhost:8080/people
$ curl -i -X POST -H "Content-Type:application/json" -d '{  "firstName" : "Frodo",  "lastName" : "Baggins" }' http://localhost:8080/people
$ curl http://localhost:8080/people/6
$ curl http://localhost:8080/people/search
$ curl http://localhost:8080/people/search/findByLastName?name=Baggins
$ curl -X PUT -H "Content-Type:application/json" -d '{ "firstName": "Bilbo", "lastName": "Baggins" }' http://localhost:8080/people/6
$ curl -X PATCH -H "Content-Type:application/json" -d '{ "firstName": "Bilbo Jr." }' http://localhost:8080/people/6
$ curl -X DELETE http://localhost:8080/people/6



*/

    
        

}
