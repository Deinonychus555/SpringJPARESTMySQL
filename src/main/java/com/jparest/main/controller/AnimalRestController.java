/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jparest.main.controller;

import com.jparest.main.repository.UserNotFoundException;
import com.jparest.main.domain.Animal;
import com.jparest.main.domain.Person;
import com.jparest.main.repository.PersonRepository;
import com.jparest.main.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/animals")
public class AnimalRestController {
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    
    @RequestMapping("/all")
    public Iterable<Animal> getAnimals (){
        
        return animalRepository.findAll();
    }
    
    @RequestMapping("name_breed/{name}/{breed}")
    public Animal getAnimal (@PathVariable String name, @PathVariable String breed){
        
        // ERROR: Esta función está mal implementada
        
        List<Animal> pp1=animalRepository.findByName(name);
        List<Animal> pp2=animalRepository.findByBreed(breed);
        Animal person=null;
        if ((!pp1.isEmpty()) && (!pp2.isEmpty())){
            Animal p1=pp1.get(0);
            Animal p2=pp2.get(0);
            if (p1.equals(p2)){
                person=p1;
            }
        }    
       return person;
    }
    
     @RequestMapping("/name/{name}")
    public List<Animal> getAnimalByName (@PathVariable String name){
        return animalRepository.findByName(name);
    }
    
     @RequestMapping("/breed/{breed}")
    public List<Animal> getAnimalByBreed (@PathVariable String breed){
        return animalRepository.findByBreed(breed);
    }
    
     @RequestMapping("/name/{id}")
    public String getAnimalName (@PathVariable String id){
        String name=null;
        
            this.validateUser(id);
            Animal person=this.animalRepository.findOne(Long.parseLong(id));
            if (person!=null){
                name=person.getName();
            }
          
        
        return name;
    }
    
    @RequestMapping("/owners/{id}")
    public List<Person> getOwner (@PathVariable String id){
        return personRepository.findByPetsIdAnimal(id);
    }
    
    @RequestMapping("name/{name}/breed/{breed}")
    public List<Animal> getNameBreed(@PathVariable String name, @PathVariable String breed){
        return animalRepository.findByNameAndBreed(name, breed);
    }
    
    
    
    
    private void validateUser(String userId) throws UserNotFoundException{
	Animal person=this.animalRepository.findOne(Long.parseLong(userId));
        if (person==null){throw new UserNotFoundException(userId);}
        
                        /* // Clase Optional<Object>
                        .orElseThrow(
				() -> new UserNotFoundException(userId));
                        */        
	
    }
    
    /*
    @RequestMapping(value="/{id}/post" ,method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
		this.validateUser(userId);
		return this.animalRepository
				.findOne(Long.parseLong(userId))
				.map(account -> {
					Bookmark result = bookmarkRepository.save(new Bookmark(account,
							input.uri, input.description));

					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.setLocation(ServletUriComponentsBuilder
							.fromCurrentRequest().path("/{id}")
							.buildAndExpand(result.getId()).toUri());
					return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
				}).get();

	}
   
    */
    
    
}
