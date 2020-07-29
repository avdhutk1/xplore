package com.avdhut.boot.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This is an example of a controller that is implemented using the contract first approach
 * The OpenAPI spec is in resources folder, petstore.yml
 * The OpenAPI codegen plugin generates the interfaces and data models
 * The interface contains all the annotations. hence you do not need them in this class
 * You can see the annotations like @RequestMapping, etc are commented and not required
 * Only the @RestController annotation is required
 * Currently only a simple log statement is implemented to verify that the request reaches the method
 */

@RestController
//@RequestMapping("/services/pet")
public class PetController implements PetApi {

    @Override
    //@DeleteMapping(path="/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") Long petId, String apiKey) {
        System.out.println("Executing Pet controller =====>");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
