# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/3.5.6/reference/html/)
* [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.6/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Profiles](https://docs.spring.io/spring-boot/reference/features/profiles.html)
* [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
* [Thymeleaf + Spring](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html)
* [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/6.6/)
* [H2 Database](https://www.h2database.com/html/main.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Validating Form Input](https://spring.io/guides/gs/validating-form-input/)
* [Spring Boot with H2](https://www.baeldung.com/spring-boot-h2-database)
* [Thymeleaf with Spring Boot](https://www.baeldung.com/thymeleaf-in-spring-mvc)
* [Spring Data JPA Query Methods](https://www.baeldung.com/spring-data-derived-queries)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

