
/*
Accessing JPA Data with REST

This guide walks you through the process of creating an application that accesses 
relational JPA data through a hypermedia-based RESTful front end.

What you’ll build

You’ll build a Spring application that let’s you create and retrieve Person objects 
stored in a database using Spring Data REST. Spring Data REST takes the features of 
Spring HATEOAS and Spring Data JPA and combines them together automatically.

*/


package com.jparest;

import com.jparest.domain.*;
import com.jparest.repository.*;


import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration

// Los componentes deben encontrarse dentro del paquete base de 'Application.Class' (main),
//para que Spring pueda encontrarlos con la anotación @ComponentScan a secas.
//Buscará anotaciones de componentes (@Controller, @Repository...)
@ComponentScan

@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class Application {
    
    /*
    @Bean 
    CommandLineRunner init(PersonRepository personRepository, AnimalRepository animalRepository, CustomerRepository customerRepository) {
		return (evt) -> {
                    
                                 // save a couple of customers
                    customerRepository.save(new Customer("Elena", "Rodriguez"));
                    customerRepository.save(new Customer("Tamara", "Ortencia"));
                    customerRepository.save(new Customer("Jack", "Bauer"));
                    customerRepository.save(new Customer("Chloe", "O'Brian"));
                    customerRepository.save(new Customer("Kim", "Bauer"));
                    customerRepository.save(new Customer("David", "Palmer"));
                    customerRepository.save(new Customer("Michelle", "Dessler"));
                    customerRepository.save(new Customer("Casandra", "Dresler"));
                    customerRepository.save(new Customer("Figaro", "Dresler"));
                    
					
                    
                    
                    Person p1 = new Person("Susana", "Bauer",19);
                    Person p2 = new Person("Miriam", "Brian",19);
                    Person p3 = new Person("Elena", "Brian",27);
                    Person p4 = new Person("Sandra", "Bauer",27);
                    Person p5 = new Person("Elena", "Palmer",18);
                    Person p6 = new Person("Mari","Megalodon", 34);
                    
                    Animal a1 = new Animal("Puskas", "cat", 2);
                    Animal a2 = new Animal("Dandil", "crocodile", 1);
                    Animal a3 = new Animal("Lassi", "dog", 5);
                    Animal a4 = new Animal("Donatello", "turtle",37);
                    Animal a5 = new Animal("Tweetie", "bird",18);
                    Animal a6 = new Animal("Rafael", "turtle",39);
                    Animal a7 = new Animal("Meg", "Megalodon",139);
                    
                    
                    p5.addPet(a6);
                    a6.addOwner(p5);
                    p5.addPet(a4);
                    a4.addOwner(p5);
                    p1.addPet(a1);
                    a1.addOwner(p1);
                    p1.addPet(a4);
                    a4.addOwner(p1);
                    p1.addPet(a5);
                    a5.addOwner(p1);
                    p2.addPet(a2);
                    a2.addOwner(p2);
                    p3.addPet(a3);
                    a3.addOwner(p3);
                    p6.addPet(a7);
                    a7.addOwner(p6);
                    
                    
                    animalRepository.save(a1);
                    animalRepository.save(a2);
                    animalRepository.save(a3);
                    animalRepository.save(a4);
                    animalRepository.save(a5);
                    animalRepository.save(a6);
                    animalRepository.save(a7);
                    
                    
                    
                    personRepository.save(p1);
                    personRepository.save(p2);
                    personRepository.save(p3);
                    personRepository.save(p4);
                    personRepository.save(p5);
                    personRepository.save(p6);
                    
                                        
                    
                    
                    
        
		};
    }
    
    @Bean
    MappingJackson2HttpMessageConverter jacksonMessageConverter() {
    MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter ();
            mc.setPrettyPrint(true);
    return mc;
}
    
*/
	public static void main(String[] args) {
            
            
            
            // SpringApplication.run(Application.class, args);
            
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        
                
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
       
        
        // save a couple of customers
        Customer customer = new Customer("Perico", "de los Palotes");
   
        customer.setShop("fnac");
    
        customerRepository.save(customer);
        
            
            /*
            
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        
                
        PersonRepository personRepository = context.getBean(PersonRepository.class);

        
        // save a couple of customers
        personRepository.save(new Person("Jack", "Bauer",19));
        personRepository.save(new Person("Chloe", "Brian",27));
        personRepository.save(new Person("Kim", "Bauer",27));
        personRepository.save(new Person("David", "Palmer",18));
        personRepository.save(new Person("Michelle", "Brian",19));
            */
	}
}

/*
The @EnableAutoConfiguration annotation provides a load of defaults (like the embedded
servlet container) depending on the contents of your classpath, and other things. 
It also turns on Spring MVC’s @EnableWebMvc annotation that activates web endpoints.
The @EnableAutoConfiguration annotation switches on reasonable default behaviors 
based on the content of your classpath. (application.properties)

The @EnableJpaRepositories annotation activates Spring Data JPA. Spring Data JPA 
will create a concrete implementation of the PersonRepository and configure it 
to talk to a back end in-memory database using JPA.

Spring Data REST is a Spring MVC application. The @Import(RepositoryRestMvcConfiguration.class) 
annotation imports a collection of Spring MVC controllers, JSON converters, and other beans 
needed to provide a RESTful front end. These components link up to the Spring Data JPA backend.
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