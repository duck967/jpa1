package com.duck.jpa1.bootstrap;


import com.duck.jpa1.services.TestService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {


    private final TestService testService;

    
    public BootStrapData(TestService testService) {
        this.testService = testService;
    }

    @Override
    //@Transactional
    public void run(String... args) throws Exception {

        //doSetup() and test() are transactional. if they defined here in BootStrapData then this method "run"
        //would have to be declared transactional. spring uses aop. due to aop the annotations of a method "b"
        //called from method "a" in the same class are invisible. thus doSetup() and test() would both have to
        //rely on the transactional declaration on the calling method "run()". 
        //
        //what if you want doSetup() to be transactional but not test(). solution is to move them to another class.
        //they can be indivyidually annotated there. they are called from this class which is of course outside of
        //their containinvg class and therefore all annotations on them will be seen.

        testService.doSetup();

        testService.test();

        testService.testLoad();

    }

}