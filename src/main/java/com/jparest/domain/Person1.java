package com.jparest.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// ¡¡IMPORTANTE!!: Al mapear el nombre de la tabla y las columnas, las mayúsculas se ignoran y 
// se convierten a minúsculas y convierte la convención de nombre 'camelCase' a separar por '_'
// Ejemplo: FirstName -> first_name

@Entity (name="Person1")
@Table(name="People1") //  La tabla de la base de datos tendrá que llamarse people
public class Person1 {
    
        private static int count=0;
        
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        @Column(name = "personZ_id",unique = true, nullable = false)
	private long id; 

	@Column(name = "idPersonZ")
       protected long idPerson;
        
        @Column(name = "firstNameZ") // La columna de la tabla ‘people’ debera llamarse ‘first_name’
        private String firstName;
        
        @Column(name = "lastNameZ")
        private String lastName;
        
        @Column(name = "ageZ")
        private int age;
        
        //@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
        @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
        @JoinTable(name = "PEOPLE_PETS", joinColumns = @JoinColumn(name = "idPerson"),
        inverseJoinColumns = @JoinColumn(name = "animal_id"))
        private List<Animal> pets= new ArrayList<Animal>();
       
        
        // ¡¡ Si implementas un constructor tienes que implementar el de defecto !!
        protected Person1() {
          
            
        } 
        
        public Person1(String firstName, String lastName) {
            this();
            this.firstName = firstName;
            this.lastName = lastName;
            this.age=firstName.length()+20;
        }   

        public Person1(String firstName, String lastName, int age) {
            this();
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }   

        

    
        public Long getIdPerson() {
        return idPerson;
    }
        
        
        
        

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        
    public String getFirstName() {
		return firstName;
	}
        
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

         public List<Animal> getPets() {
            return pets;
        }

        public void setPets(List<Animal> pets) {
            this.pets = pets;
        }
        
         public void addPet(Animal mascota) {
            this.pets.add(mascota);
        }
        
        
        
        public boolean equals (Person1 person){
            
            return this.idPerson==person.getIdPerson();
            
        }
        
        public String toString(){
            String template="Soy %s %s";
            return String.format(template, this.firstName, this.lastName);
        }
}
