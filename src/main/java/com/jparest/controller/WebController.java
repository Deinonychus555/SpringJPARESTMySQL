package com.jparest.controller;

// Validating Form Input 
import com.jparest.domain.Person;
import com.jparest.repository.PersonRepository;
import java.util.concurrent.atomic.AtomicLong;
import javax.validation.Valid;
// Validating Form Input 
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/web")
public class WebController extends WebMvcConfigurerAdapter {
    
    AtomicLong counter = new AtomicLong();
    
    @Autowired
    private PersonRepository personRepository;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        
        ViewControllerRegistration registration;
        registration = registry.addViewController("/results");
        registration.setViewName("results");
        
        // registry.addViewController("results").setViewName("results");
    }

    // The showForm method returns the form template. 
    // It includes a Person in its method signature so the template can associate 
    //form attributes with a Person.
     @RequestMapping(value="/form", method=RequestMethod.GET)
    public String showForm(Person person, Model model) {
        
        int count =(int) counter.incrementAndGet();
        model.addAttribute("count", count);
        return "form";
    }
    
    // The checkPersonInfo method accepts two arguments:
    //- A person object marked up with @Valid to gather the attributes filled out in the form you’re about to build.
    //- A bindingResult object so you can test for and retrieve validation errors.


    @RequestMapping(value="/form", method=RequestMethod.POST)
    public String checkPersonInfo(Person person) {
        //System.out.println(person.toString());
        this.personRepository.save(person);
        return "results";
    }
    
    
}
