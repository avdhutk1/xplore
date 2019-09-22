package com.audhut.j8ex;

import com.audhut.j8ex.objects.Car;
import com.audhut.j8ex.objects.Insurance;
import com.audhut.j8ex.objects.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by avdhut on 8/7/18.
 */
public class OptionalEx {
    /*
    Java8 added the Optional class to prevent libraries/methods from returning null values.
    It also removes the need for null checks making the code more readable
    If the method returns a Optional class, it clearly indicates to the user of that method
    that the value is not mandatory
    */

    public static void main(String[] args){

        final Logger logger = LoggerFactory.getLogger(OptionalEx.class);

     /* 3 ways to create a Optional - using
         1. empty - to create a empty object
         2. of - usually using a non-null value
         3. of Nullable - returns optional with value and if not, returns an empty
          */

        Person person;
        //An empty optional
        Optional<Person> optPer = Optional.empty();
        //using isPresent to check if it has a value
        //dont use == to compare instances as it can give unpredictable results. Use isPresent
        logger.debug("The value is present in the optional created with empty - {}", optPer.isPresent());

        //For a non-null creation use 'of' - it does a compile time check for initialization
        //uncomment the below to get error
        //optPer =Optional.of(person);
        //Similarly for of Nullable
        //optPer = Optional.ofNullable(person);

        person = new Person();
        optPer =Optional.of(person);
        logger.debug("The value is present in the optional created with of - {}", optPer.isPresent());
        optPer = Optional.ofNullable(person);
        logger.debug("The value is present in the optional created with ofnullable - {}", optPer.isPresent());

        person=null;
        //throws a nullpointer exception if 'of' is used with a null object
        //uncomment to see nullpointer error
        //optPer =Optional.of(person);
        //logger.debug("The value is present in the optional created with of and null assigned -  {}", optPer.isPresent());
        //however in case of ofnullbale, it returns an empty optional
        optPer = Optional.ofNullable(person);
        logger.debug("The value is present in the optional created with ofnullable  and null assigned - {}", optPer.isPresent());


        /* Optional as Streams
        Optional has a map method where the original type is converted to a different type using the map func
        If the map func can result in a null, it is transformed to an Optional with type of the transformed object
        This is similar to a stream, where the Optional can be considered as a stream with one single value
        map and flatmap functions - take the value of Optional if present and applies the map func on it
        If not present, it returns an empty Optional
         */
        Insurance ins = new Insurance("health");
        Optional<Insurance> optins = Optional.ofNullable(ins);
        //If you want to transform this to a string which might be null, then the map method can be used
        Optional<String> name = optins.map(Insurance::getName);
        logger.debug("optional transformed using map - {}", name.get());

        //in the above case, getName was a method that returned a string
        //what happens if the mapping func returns an optional. i.e chain of methods
        //in this case, we get a stream of Optional<Optional<T>>
        //to prevent this you can use flat map

        Car crr = new Car();
        crr.setInsurance(optins);

        person = new Person();
        optPer = Optional.of(person);
        person.setCar(Optional.of(crr));
        //below chaining of Optional results in error due to creation of nested stream Optional<Optional<T>>
        //Optional<String> namemap = optPer.map(Person::getCar).map(crr::getInsurance).map(ins::getName);
        //Hence use flatmap which replaces the generated stream,i.e Optional in this case, by the contents
        //i.e it flattens them into a single stream
        //Here the last one does not need flatmap as its return value is not Optional
        Optional<String> namemap = optPer.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName);
        logger.debug("chaining optional methods using flatmap - {}", namemap.isPresent());

        /* Optional is not serializable. Hence it is better to avoid it in Domain models
         In our example of Person, Car above, we have used it in domain model. Which is wrong
         It is mainly used in Optional only return values for business logic
         You can have a method in the domain model that specifically returns an optional. For eg. getCarAsOptional instead of getCar
         */

        //Getting the values in Optional
        //get is a method that returns a value if exist else it throws a noSuchElement exception
        Car ecar =  person.getCar().get();
        logger.debug("using get to get a value from optional - {}", Objects.isNull(ecar));
        //setting a null car
        person.setCar(Optional.empty());
        //uncomment to see the exception
        //ecar =  person.getCar().get();
        //OrElse method can give a default value if the value does not exist
        ecar = person.getCar().orElse(new Car());
        logger.debug("example of a orElse returning a default value - {}", Objects.isNull(ecar));

        //combining two optional without unwrapping them
        //If you have a method with two optional inputs, generally you would unwrap them with ifPresent method checks
        //You can avoid it using flatmap
        OptionalEx opx = new OptionalEx();
        Optional<Car> opcr = Optional.of(crr);
        Optional<Insurance> rints = opx.nullSafeCheapIns(optPer, opcr);
        logger.debug("example of combining 2 optional without unwrapping - the insurance name is {}", rints.get().getName());

        //Filter method
        //The filter method on Optional is also similar to the map. It takes the filter predicate, applies
        //it if it has value and if it has no value returns an empty optional
        //Used to check if the Optional type has some value
        Optional<Insurance> optfil = Optional.ofNullable(new Insurance("Bajaj"));
        //uncomment below to see an empty optional
        //Optional<Insurance> optfil = Optional.empty();
        optfil.filter(insurance->"Bajaj".equals(insurance.getName())).ifPresent(x->logger.debug("name of insuranance is {}", x));

        //Uses of Optional
        //Some methods like 'get' of a map return null if the key does not exist
        //Instead of having the dirty check of if-else and returning the value, an optional can be returned
        Optional<String> rs = opx.getVal("somekey");

        /* similarly, you have methods that throw exceptions which could also be wrapped in an Optional
        one example is parseInt method which takes a string and gives back the Integer
        This method throws an exception, instead of propagating the exception to the client
        you can wrap it in an optional and send it back
        Preferably you can have a Optional Utilities class that could do all such handling
        */
        Optional<Integer> oint = opx.getInt("stringWithoutInt");

        /* There are primitive Optionals like OptionalInt, etc. But they lack flatmap,map methods
        Usually in streams you use primitives for streams containing large values to improve performance
        Here as Optional has only one value, it makes no difference
        preferably avoid using these
         */

        /* Where you see a lot of if-else code which checks null valu, etc. You can replace that with
        the use of Optionals.
        You can write a single line code with chaining methods by using Optional.
        Example of an Optional getting a value from properties.
        Optional.ofNullable(props.getProperty(name).flatmap(OptionalUtil::stringToInt).filter(i->i>0).ifElse(....
         */
    }

    //typically this method would use the isPresent method to unwrap values
    //this results in a lot of if-else code that can be avoided using flatmap
    public Optional<Insurance> nullSafeCheapIns(Optional<Person> op, Optional<Car> oc){
        return op.flatMap(p->oc.map(c->findCheapestInsurance(p,c)));
    }

    //a business method to return cheapest insurance
    public Insurance findCheapestInsurance(Person p, Car c){
        Insurance cins = new Insurance("vw");
        return cins;
    }

    public Optional<String> getVal(String key){
        HashMap<String, String> mp = new HashMap<>();
        mp.put("key1", "hello");

        //here value of the key could be null
        //instead of if-else,return an optional wrapped using ofnullable
        Optional<String> ropstr = Optional.ofNullable(mp.get(key));
        return ropstr;
    }

    public Optional<Integer> getInt(String val){

        //return a Optional if string does not have any integer
        try{
            return Optional.ofNullable(Integer.parseInt(val));
        }catch (NumberFormatException e){
            //catch the exception and return an empty Optional
            return Optional.empty();
        }
    }

}
