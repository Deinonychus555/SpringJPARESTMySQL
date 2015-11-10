package com.jparest.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//http://localhost:8080/

@Controller
public class FileUploadController {
    
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String provideUploadInfo() {
        return "form2";
    }
    
    
    // The handleFileUpload method is geared to handle a two-part message: 
    //name and file. It checks to make sure the file is not empty, and if it is empty, 
    //the method grabs the bytes. Next, it writes them out through a BufferedOutputStream.
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
}

/*
@Controller annotation says that every method returns a view. 

The @ResponseBody annotation tells Spring MVC not to render a model into a view,
but rather to write the returned object into the response body.


The @RequestMapping annotation ensures that HTTP requests to /greeting are mapped 
to the greeting() method.

@RequestParam binds the value of the query string parameter name into the name 
parameter of the greeting() method. 
*/