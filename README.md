## Week 5

#### Ideal flow of the learning track

In this week, students can be introduced to Spring boot. They should know the difference with regular
Spring, and they should learn what Spring boot brings to the table. In week 5, students get more familiar
with Spring and what it can do for them. 

They should also get introduced to multithreading. Not the actual concurrency/multithreading api in Java, 
that would not be feasible. But they should understand that the very nature of spring beans (which are actually by
default one of a kind objects) has an impact on stateful server applications. Spring boot is used a lot
for services with simple REST-like api's, which can have hundreds of concurrent users. Holding state in
objects that are being reused by different users, is a recipe for disaster.

Spring boot comes with a lot of functionality, in this week, using spring boot, they should start building
REST-like API's and as an extra, look in to (simple) security. 

Introduce REST clients. Students should also be able to consume REST (like) apis. This presents the opportunity
to consume custom api's with bugs, which they need to find and fix. We introduce actual debugging, using break points, 
reading stacktraces, etc. Testing api's can be done with tools like Postman. 


* Students should learn what Spring Boot is and what the benefits are over regular spring
* Students really need to get the caveat with singleton beans and state
* Introduce spring web with REST like apis. Students should also understand what REST is. Many developers don't really know. 
* Students should learn how to consume apis by building clients. These clients can also be used to test the apis they have
  created, but they can also use tools like Postman. 
* Debugging code, using their IDE. Reading, interpreting stack traces, etc


#### Goals for week 5:

* Understand what spring boot is and what improvements it brings compared to regular spring
* Be able to explain the impact of state on singleton beans in a multithreaded environment
* Know 1 more bean scope: Request (maybe as an extra, I have used it every once and a while, but maybe it is not that important)
* Understand what a REST api is and have an idea on what the standards are.
* Be able to build a REST like api with Spring boot, some simple security could be an extra
* Be able to build a REST client to consume api's
* Be able to work with Postman to test/consume an API
* Be able to debug code, read and react to stack traces

#### challenge idea

- create an api for the students which is flawed. They need to build a consumer of that api, and find the bug
  using their knowledge of debugging, stack traces, etc etc. 
