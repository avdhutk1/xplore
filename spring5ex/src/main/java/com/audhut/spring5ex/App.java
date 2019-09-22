package com.audhut.spring5ex;

import com.audhut.spring5ex.config.AppConfig;
import com.audhut.spring5ex.impl.*;
import com.audhut.spring5ex.intf.CompactDisc;
import com.audhut.spring5ex.intf.MessageRenderer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * This is the main application to test simple spring application and understand DI
 * There are some features like lookup method and method replacement that manipulate byte code
 * to change methods. It is not recommended to use these. The only use is in case of legacy beans
 * where you want to change the method logic
 * Another feature is if a bean wants access to ApplicationContext. It needs this to obtain a dependent bean
 * for eg. ctx.getbean('dependent bean'). In this case, Spring is not aware as the DI is not used
 * For this the bean must implement ApplicationAwareContext interface. Once this is done, Spring is aware
 * and ensures that dependent beans are instantiated first. annotation is @DependsOn('dependentBean') added to
 * the bean that implements the ApplicationContextAware bean
 *
 */
public class App {
    public static void main(String[] args) {
        /*
        simple BeanFactory to check the spring initialization
        The Applicationcontext, which extends BeanFactory, is mainly used and the below is commented
         */
        /*
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader rdr = new XmlBeanDefinitionReader(factory);
        rdr.loadBeanDefinitions(new ClassPathResource("beans.xml"));
        */
        /*
        Here the xml config is used along with classes annotated with stereotypes
        The xml has only one tag called 'component scan' with the packages to be scanned
        This ensures that all stereotype beans are scanned and detected by spring
         */

        /*ApplicationContext appctx  = new ClassPathXmlApplicationContext("beans.xml");
        MessageRenderer mr = appctx.getBean("renderer", MessageRenderer.class);
        mr.render();*/


        /*
        The below block is used for Java configuration.
        In this the beans are defined in Java class
         */

       /* ApplicationContext appctx  = new AnnotationConfigApplicationContext(AppConfig.class);
        //uncomment the below for beans defined in the Java class
        //MessageRenderer mr = appctx.getBean("rendererannot", MessageRenderer.class);
        //the below bean is using Java and component scan
        MessageRenderer mr = appctx.getBean("renderer", MessageRenderer.class);
        mr.render();*/

       /*
       In this example, a prototype, i.e non-singleton beans are created
        */
        //ApplicationContext appctx  = new ClassPathXmlApplicationContext("beans.xml");
       /* ApplicationContext appctx  = new AnnotationConfigApplicationContext(AppConfig.class);
        Singer sing1 = appctx.getBean("non-singleton", Singer.class);
        Singer sing2 = appctx.getBean("non-singleton", Singer.class);
        System.out.println("both singers are same  ==> " +  (sing1 == sing2));
        System.out.println("hascode of sing1 2is ==>" + sing1);
        System.out.println("hascode of sing1 is =>" + sing2);*/

        //this example tests the lifecycle method of init
        /*ApplicationContext appctx  = new AnnotationConfigApplicationContext(AppConfig.class);
        Musician ms = appctx.getBean("musician", Musician.class);
        System.out.println("name set in init method is ==> " + ms.getName());*/

        //example of a bean using a factory bean and method
        /*ApplicationContext appctx  = new ClassPathXmlApplicationContext("beans.xml");
        MessageDigester msgdig = appctx.getBean("messageDigester",MessageDigester.class);
        msgdig.digest("Hello world!");*/


        /*ApplicationContext appctx  = new ClassPathXmlApplicationContext("beans.xml");
        Orchestrator orch = appctx.getBean("orch",Orchestrator.class);
        orch.processOrder();*/

        //This is an example of how a constructor injection happens in java config
        /*ApplicationContext appctx  = new AnnotationConfigApplicationContext(AppConfig.class);
        CDPlayer cdp = appctx.getBean("cdplayer", CDPlayer.class);
        cdp.play();
        */

        //This is an example of a conditional bean creation
       /* ApplicationContext appctx  = new AnnotationConfigApplicationContext(AppConfig.class);
        DependentBean depbean = appctx.getBean("dependentBean", DependentBean.class);
        if (depbean!= null)
            System.out.println("Dependent bean is created");
        else
            System.out.println("Dependent bean is not created");*/

        //this is example of autowiring ambiguities and resolving them using custome annotation and Qualifier
        /*ApplicationContext appctx = new AnnotationConfigApplicationContext(AppConfig.class);
        Dinner dinner = appctx.getBean("dinner", Dinner.class);
        dinner.serveDessert();*/

        //this is an example of dynamic properties being injected into the class
        ApplicationContext appctx = new AnnotationConfigApplicationContext(AppConfig.class);
        CompactDisc blankdisc = appctx.getBean("blankDisc", BlankDisc.class);
        blankdisc.play();
    }
}