package com.jparest.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name="Customer")
@Table(name="Customers")
public class Customer extends Person1 {
    
   
    
   //protected long idPerson;
        
   
   //private String firstName;
        
        
   //private String lastName;
       
    //private int age;
        
    
    public Customer (){
        super();
        
    }
    
    public Customer (String firstName, String lastName) {
        
        super(firstName, lastName);
    }

    private String shop;
    
    public String getShop(){
        
        return this.shop;
    }
    
    public void setShop( String shop) {
        
        this.shop = shop;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                getIdPerson(), getFirstName(), getLastName());
    }

}

