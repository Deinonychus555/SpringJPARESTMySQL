/*
http://localhost:8080/people/all

http://localhost:8080/people/firstName/Susana/lastName/Bauer

http://localhost:8080/people/firstName/Susana/age/19

http://localhost:8080/people/pets/1

 */
package com.jparest.main.controller;

import com.jparest.main.repository.UserNotFoundException;
import com.jparest.main.domain.Animal;
import com.jparest.main.domain.Person;
import com.jparest.main.repository.PersonRepository;
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
@RequestMapping("/people")
public class PersonRestController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @RequestMapping("/all")
    public Iterable<Person> getPeople (){
        
        return personRepository.findAll();
    }
    
    @RequestMapping("/first_last_name/{first_name}/{last_name}")
    public Person getPerson (@PathVariable String first_name, @PathVariable String last_name){
        
        // ERROR: Esta función está mal implementada
        
        List<Person> pp1=personRepository.findByFirstNameIgnoreCase(first_name);
        List<Person> pp2=personRepository.findByLastNameOrderByFirstNameAsc(last_name);
        Person person=null;
        if ((!pp1.isEmpty()) && (!pp2.isEmpty())){
            Person p1=pp1.get(0);
            Person p2=pp2.get(0);
            if (p1.equals(p2)){
                person=p1;
            }
        }    
       return person;
    }
    
    @RequestMapping("/first_name/{name}")
    public List<Person> getPersonByFirstName (@PathVariable String name){
        return personRepository.findByFirstNameIgnoreCase(name);
    }
    
    @RequestMapping("/optional_first_name/{name}")
    public Person getPersonByOptionalFirstName (@PathVariable String name){
        Optional <Person> optionalPerson = personRepository.findByFirstName(name);
        return optionalPerson.orElseThrow(()-> new UserNotFoundException(name));
    }
    
    @RequestMapping("/years/{years}") // http://localhost:8080/people/years/26
    public List<Person> getPersonByYears(@PathVariable String years){
        return personRepository.findByYears(Integer.parseInt(years));
    }
    
    @RequestMapping("/years2/{years1}/{years2}")
    public List<Person> getPersonByYears2(@PathVariable String years1, @PathVariable String years2){
        // @Query("select u from Person u where u.age > ?1 and u.age < ?2")
        return personRepository.findByYears2(Integer.parseInt(years1), Integer.parseInt(years2));
    }
    
    @RequestMapping("/years3") // http://localhost:8080/people/years3?years=27
    public List<Person> getPersonByYears3(@Param ("years") String years){
        return personRepository.findByYears(Integer.parseInt(years));
    }
    
    
     @RequestMapping("/last_name/{last_name}")
    public List<Person> getPersonByLastName (@PathVariable String last_name){
        return personRepository.findByLastNameOrderByFirstNameAsc(last_name);
    }
    
    
    
    
     @RequestMapping("/name/{id}")
    public String getPersonName (@PathVariable String id){
        String name=null;
        
            this.validateUser(id);
            Person person=this.personRepository.findOne(Long.parseLong(id));
            if (person!=null){
                name=person.getFirstName();
            }
          
        
        return name;
    }
    
    @RequestMapping("/pets/{id}")
    public List<Animal> getPets(@PathVariable String id){
        this.validateUser(id);
        return animalRepository.findByOwnersIdPerson(id);
    }
    
    
    @RequestMapping("firstName/{firstName}/lastName/{lastName}")
    public List<Person> getFirstNameLastName(@PathVariable String firstName, @PathVariable String lastName){
        
        return personRepository.findByFirstNameAndLastNameAllIgnoreCase(firstName, lastName);
    }
    
    @RequestMapping("firstName/{firstName}/age/{age}")
    public List<Person> getFirstNameAge(@PathVariable String firstName, @PathVariable String age){
        
        return personRepository.findByFirstNameAndAge(firstName, Integer.parseInt(age));
    }
    
    @RequestMapping("firstName/{firstName}/petName/{petName}")
    public List<Person> getFirstNamePetName(@PathVariable String firstName, @PathVariable String petName){
        
        return personRepository.findByFirstNameAndPetsName(firstName, petName);
    }
    
    
    @RequestMapping("savePerson/{firstName}/{lastName}")
    public Person savePerson(@PathVariable String firstName, @PathVariable String lastName) {
        
        Person person = new Person (firstName, lastName);
        personRepository.save(person);
        
        return person;
    }
    
    
    private void validateUser(String userId) throws UserNotFoundException{
	this.personRepository.findByIdPerson(userId)
                
                 .orElseThrow(() -> new UserNotFoundException(userId));
				
     }
    
    // ¿http://localhost:8080/people/4/post/{"name":"Rasca", "breed":"mouse"}?
    @RequestMapping(value="/{id}/post" ,method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String id, @RequestBody Animal input) {
		this.validateUser(id);
                
		return this.personRepository
				.findByIdPerson(id)
				.map(person -> {
                                        /*
                                        Animal animal=new Animal(input.getName(), input.getBreed(), input.getAge());
                                        animal.setOwner(person);
                                        Animal result = animalRepository.save(animal);
                                        */        
					Animal result = animalRepository.save(new Animal(
							input.getName(), input.getBreed(), person));

					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setLocation(ServletUriComponentsBuilder
							.fromCurrentRequest().path("/{id}")
							.buildAndExpand(result.getId()).toUri());
					return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
				}).get();

	
    }
    
  
    
    /*
    @RequestMapping(value="/put",method=RequestMethod.PUT)
    public Person addPerson(@RequestBody Person person){
        return personRepository.save(person);
    }
    
    
    
    */
}
