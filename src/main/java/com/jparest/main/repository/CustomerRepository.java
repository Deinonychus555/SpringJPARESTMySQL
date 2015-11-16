

package com.jparest.main.repository;


import com.jparest.main.domain.Customer;
import com.jparest.main.domain.Person;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByShop(@Param("shop") String shop);
    
    List<Customer> findAll();
    
     @RestResource(path = "lastNames") // El path va en plural.
	List<Customer> findByLastNameOrderByFirstNameAsc(@Param("lastName") String lastName);
        
        
        @RestResource(path = "names", rel="names") // El path va en plural.
        // Ej: http://localhost:8181/people/search/names
        List<Customer> findByFirstNameIgnoreCase(@Param("firstName") String firstName);
}


