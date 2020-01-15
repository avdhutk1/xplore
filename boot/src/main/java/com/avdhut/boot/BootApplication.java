package com.avdhut.boot;

import com.avdhut.boot.config.ModuleConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

/**
 * This project explores Spring Boot and Sprint REST
 * Spring boot startup requires a class with @SpringBootApplication annotation
 * This annotation is a combination of 3 different annotations
 * 1. @Configuration - you can define @Bean methods in this class
 * 2. @ComponentScan - this class needs to be present in the base package path. It scans all packages from
 * that base path
 * 3. @EnableAutoConfiguration - Spring boot detects what dependencies you have defined and accordingly configures
 * the app. For eg. If web-starter is included, it configures the app as a web app with tomcat,dependent lib, etc
 * You can either use one of @SpringBootApplication or the @EnableAutoConfiguration with the other annotation
 * This is useful if you want to disable/exclude some autoconfig libraries. You can do it by:
 * @EnableAutoConfiguration(exclude=(DatSourceAutoConfiguration.class))
 * You can also use the 'exclude' expresssion with the @SpringBootApplication annotation
 * Spring and its projects like SpringCore, SpringData, SpringCloud, Springtransaction, etc provide annotation like
 * @Enable<Technology> - eg @EnableTransactionManagement, @EnableIntegration, etc. This is on Spring principle of
 * convention over configuration
 * @EnableAutoConfiguration internally uses the above @Enable<tech> to auto configure the application
 * To find out what autoconfig is enabled, start the application with --debug flag
 *
 * Importing other config classes
 * It is possible to import other config classes using the @import annotation. see example below
 * where a moduleconfig class creates a moduleservice bean and used in the controller
 * refer to moduleService class. It does not use @component annotation and hence it is created by calling the
 * @bean method
 *
 *
 * Customizing banner
 *  Many ways to customize banner and startup details
 *  1. in-line print the banner as given below. Uncomment out to see
 *  2. Place Banner.txt in resources where it picks from default
 *  3. You can have another place like resources/META-INF/banner.txt - the meta-inf directory of jar
 *  In this case you have to specify the arg -Dbanner.location=classpath:/META-INF/banner.txt
 *  You can also have the property banner.location=classpath:/.. in the application.yml files
 *  You can also have additional properties as added in banner.txt. see example banner.txt
 *  The spring  expression values are taken from the Manifest.mf file of the jar
 *
 * SpringApplicationBuilder class
 * Is another fluent API that can be used instead of SpringApplication
 * It provides hierarchy support like importing other configs as a child app
 * You can also add ApplicationListeners. See below example for ApplcationListerners
 * You can also add listeners by configuring a file, /META-INF/spring.factories by using a key
 * org.springframework.context.ApplicationListener=com.example.project.MyListener
 * usually the application doe snot need it and is used internally by spring but there are different events
 * like ApplicationStartingEvent, ApplicationStartedEvent, ApplicationReadyEvent, etc
 *
 * It is often desirable to call setWebApplicationType(WebApplicationType.NONE) when
 * using SpringApplication within a JUnit test
 *
 * Application Arguments
 * Application args that are specified while running the jar/app can be accessed in a bean also
 * The bean constructor needs to have an arg of ApplicationArguments that is autowired
 * See the AppArgsService class as an example
 *
 * Code execution before application start
 * It is possible to execute code once the application starts
 * This is possible by implementing the interfaces ApplicationRunner and CommandLineRunner
 * Preferably ApplicationRunner is to be used
 * The main application class can also implement this or a different class
 * Refer to the PreInitservice class as an example
 *
 * Externalize configuration
 * Spring boot supports external configurations via different methods - env variables, application.properties
 * application.yml are some of the common ways
 * The values can be injected via the Spring Environment abstraction, @Value annotation or
 * @ConfigurationProperties annotation to bind config variables to object properties
 * The order of overiding the config variables are :
 * 1. Command line variables
 * 2. SPRING_APPLICATION_JSON
 * 3. JNDI
 * 4. System.getProperties()
 * 5. OS env variables
 * 6. RandomValuePropertySource(random.*)
 * 7. Profile specific (application-{profile}.properties) outside of package jar
 * 8. Profile specific (application-{profile}.properties) inside of package jar
 * 9. application.properties (or yml) outside of package jar
 * 10.application.properties (or yml) outside of package jar
 * 11. @PropertySource
 * 12. SpringApplication.setDefaultProperties
 * Refer Appconfig class where values are injected from application.yml
 * Refer the GreetingServiceImpl for injecting command line args
 * They are injected using ...jar --server.ip=5.5.5.5 or using spring.application.json
 *
 * Relaxed binding
 * Spring uses relaxed binding of property names
 * Recommended property name in yml is dashed notation or kebab notation
 * for eg. message-destination-name.
 * It also supports uppercase - used for env values e.g MESSAGE_DESTINATION_NAME.
 * property value can be retrieved using dot notation in this case - message.destination.name
 * The other format supported is camel case
 *
 * Location of application.prop
 * Changing location and name of application.properties file
 * spring looks for the application.prop file in the following order
 * 1. /config sub-directory in the current directory
 * 2. current directory
 * 3. classpath /config package
 * 4. classpath root, i.e src/main/resources
 *You can change the name and location by specifying the following props in the command line
 * java -jar myjar.jar --spring.config.location=file:app/ --spring.config.name=myconfig
 * where the file is present in a folder called app and the name is myconfig.
 * you do not need the extension. Spring will add .properties or yml to it
 * Search order for props file is also
 * classpath,classpath:/config, file:./, file:./config/, unless the location is changed
 * You can also have profiles with the names as application-{profile}.yml
 * application-prod.yml and activate it with the command line property spring.profiles.active=prod
 *
 * Injecting using @Value and @ConfigurationProperties
 * Injecting External configuration using @Value and @ConfigurationProperties
 * You can directly inject the values from application.properties using @Value
 * Refer to AppConfig class for examples
 * If you want to bind properties to a Bean use @ConfigurationProperties
 * Refer to AcmeProperties bena which has properties injected from application.yml
 * It also has a nested bean, Security, that has list and map being injected
 * Binding on Static properties is not supported
 * In such cases, the beanname is <prefix>-<fqdn> - for eg. acme-com.audhut.boot.domain.AcmeProperties
 * Refer to Acme properties for how binding to bean and nested bean is done
 * Also refer to Security bean on how mapping to lists and maps is done
 *
 *
 */



@SpringBootApplication
@Import(ModuleConfig.class)
public class BootApplication {


	public static void main(String[] args) {

		//uncomment the below if you do not want to customize the Spring app at start up
		//SpringApplication.run(BootApplication.class, args);

		//this will enable to customize the spring app
		SpringApplication springapp = new SpringApplication(BootApplication.class);

		//uncomment below to see in-line banner
		//springapp.setBanner((env, cls, out) -> out.print("\n\n\tThis is my own banner!\n\n".toUpperCase()));

		//uncomment to set banner mode off
		//springapp.setBannerMode(Banner.Mode.OFF);
		springapp.run(args);

		//Example of SpringApplicationBuilder - uncomment to use it instead of SpringApplication
		/*new SpringApplicationBuilder(BootApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.listeners((e) -> System.out.println(e.getClass().getCanonicalName()))
				.run(args);
		*/


	}

}
