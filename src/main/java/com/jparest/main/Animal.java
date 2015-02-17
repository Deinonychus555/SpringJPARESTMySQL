package com.jparest.main;

import com.jparest.main.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="animals")
public class Animal {
    
        private static int count=0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "animal_id")
	private long id; 
        
        @Column(name = "id")
        private String idAnimal;

        @Column(name = "name")
        private String name;
        
        @Column(name = "breed")
        private String breed;
        
        @Column(name = "age")
        private int age;
        
        @JsonIgnore // Para que cuando muestre el ususario este no muestre sus mascotas
        /*
        @ManyToOne(cascade = CascadeType.ALL, optional = false)
        @JoinColumn(name = "person_id")
        */
        @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE} )
        private List<Person> owners=new ArrayList<Person>();
        
        // ¡¡ Si implementas un constructor tienes que implementar el de defecto !!
        protected Animal() {
            this.idAnimal=String.valueOf(count++);
                
        } 
        
         public Animal(String name, String breed){
            this();
            this.name = name;
            this.breed = breed;
            this.age=name.length()+10;
        } 

        public Animal(String name, String breed, int age ){
            this();
            this.name = name;
            this.breed = breed;
            this.age=age;
        } 
        
        public Animal(String name, String breed, Person owner ){
            this();
            this.name = name;
            this.breed = breed;
            this.age=name.length()+10;
            this.owners.add(owner);
        }  
        
        
        public Animal(String name, String breed, int age, Person owner ){
            this();
            this.name = name;
            this.breed = breed;
            this.age=age;
            this.owners.add(owner);
        }  

    public String getIdAnimal() {
        return idAnimal;
    }
        
        

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getOwners() {
        return owners;
    }

    public void setOwners(List<Person> owners) {
        this.owners = owners;
    }

   
    public void addOwner(Person person){
        this.owners.add(person);
    }

    public long getId() {
        return id;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

        
        
        public String toString(){
            String template="Soy %s";
            return String.format(template, this.name);
        }
}
