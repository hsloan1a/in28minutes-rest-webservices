package com.in28minutes.rest.webservices.restfulwebservices.version;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersionController {

    @GetMapping("/v1/person")
    public PersonV1 getPersonV1(){
        return new PersonV1("someone thought-full");
    }

    @GetMapping("/v2/person")
    public PersonV2 getPersonV2(){
        return new PersonV2(new Name("Someone", "Thought-full"));
    }

    @GetMapping(value="/person", params = "version=1")
    public PersonV1 getPersonV1param(){
        return new PersonV1("someone thought-full");
    }

    @GetMapping(value="/person", params = "version=2")
    public PersonV2 getPersonV2param(){
        return new PersonV2(new Name("Someone", "Thought-full"));
    }

    @GetMapping(value="/person", headers = "X-API-VERSION=1")
    public PersonV1 getPersonV1header(){
        return new PersonV1("someone thought-full");
    }

    @GetMapping(value="/person", headers = "X-API-VERSION=2")
    public PersonV2 getPersonV2header(){
        return new PersonV2(new Name("Someone", "Thought-full"));
    }

    @GetMapping(value="/person", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getPersonV1produces(){
        return new PersonV1("someone thought-full");
    }

    @GetMapping(value="/person", produces = "application/vnd.company.app-v2+json")
    public PersonV2 getPersonV2produces(){
        return new PersonV2(new Name("Someone", "Thought-full"));
    }
}
