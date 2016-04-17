# upload_project

## Java 8 required

Run tests using `mvn test`

Start application using `mvn spring-boot:run`

Default Max File size if 512 kbs.

Users available
Login: admin Password: admin_password
Login: user Password: password
Login: user2 Password: user2


I've disabled CSRF for this application mainly because it was an annoyance to test. Any production grade app should have it enabled. 

Tests are located in src/test/java/hello

The rest of the classes are in src/main/java/hello