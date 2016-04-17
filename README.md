# upload_project

## Java 8 required

Run tests using `mvn test`

Start application using `mvn spring-boot:run`

Default Max File size if 512 kbs.

Users available

| Login | Password      |Role   |
|-------|---------------|-------|
| admin | admin_password| ADMIN |
| user  | password      | USER  |
| user2 | password2     | USER  |


I've disabled CSRF for this application mainly because it was an annoyance to test. Any production grade app should have it enabled. 

Tests are located in src/test/java/hello

The rest of the classes are in src/main/java/hello

There's a working copy @ http://spring-boot-upload.herokuapp.com.

All data is held in memory and will be removed upon application restart. 
