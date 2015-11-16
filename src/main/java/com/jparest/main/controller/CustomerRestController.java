/*
http://localhost:8080/people/all

http://localhost:8080/people/firstName/Susana/lastName/Bauer

http://localhost:8080/people/firstName/Susana/age/19

http://localhost:8080/people/pets/1

 */
package com.jparest.main.controller;

import com.jparest.main.repository.UserNotFoundException;
import com.jparest.main.domain.Animal;
import com.jparest.main.domain.Customer;
import com.jparest.main.domain.Person;
import com.jparest.main.repository.CustomerRepository;
import com.jparest.main.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;



@RestController
@RequestMapping("/customers")
public class CustomerRestController {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @RequestMapping("/all")
    public Iterable<Customer> getPeople (){
        
        return customerRepository.findAll();
    }
    
     @RequestMapping("/first_name/{name}")
    public List<Customer> getPersonByFirstName (@PathVariable String name){
        return customerRepository.findByFirstNameIgnoreCase(name);
    }
    
   
    
    
    /*
    @RequestMapping(value="/put",method=RequestMethod.PUT)
    public Customer addCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }
    
    
    
    */
}
