# Engineering thesis
### Test Driven Development of web applications based on Java Spring [[PDF](https://github.com/hlebshypulahub/AGH-Hleb-Shypula/blob/master/src/main/resources/doc/Praca_Inżynierska_Hleb_Shypula.pdf "pdf")]

###### The repository for back-end part of the application.

<div align="center">
  Based on this application was built the <a href="https://github.com/hlebshypulahub/Course-Manager-Back"><strong>Course Manager »</strong></a>
</div>

<br/>

## Short description

The main goal of the thesis was to write a Java Spring-based web application using the Test-Driven Development methodology. Namely:
- presentation of the basic features and patterns of the Test-Driven Development methodology
- gaining personal experience in test-driven programming
- checking if the level of entry to this methodology is acceptable for a programmer not very experienced in any testing

The main affirmation of the thesis is that thanks to TDD, the code will have much better quality. If so, then writing the code required for a positive test result produces a sub affirmation – the developer will be able to deliver a fully functional product with added new functionality literally daily. Thanks to this, cooperation with the client will reach a new level.

The task of the application, which illustrates the usage of the TDD methodology, is to control and support the procedure and conditions for professional certification of medical, pharmaceutical and other healthcare workers in accordance with the regulation of the Ministry of Health of Belarus of May 28, 2021.

The server part of the application was implemented using Java Spring technology, as it results from the topic of the thesis.

The client's website was written using the JavaScript ReactJS library. Here is the front-end repository of [Course manager »](https://github.com/hlebshypulahub/Course-Manager-Front "Course manager »").

The implementation chapter shows the implementation of the most significant TDD patterns. The unit and integration testing of service-classes, controller-classes and repository-interfaces was demonstrated as well. It was also shown how the authorization and user authentication using the JWT token in Spring Security work. At the end of the implementation chapter, the final design of the application is shown along with a tree of directories and packages.

Summing up the work, the possibilities of further development of the application were presented. Referring to the main goals of the thesis, it can be said that it was possible to gain valuable experience not only in TDD, but also in a whole range of other technologies. Answering the question: "Is the entry level to this methodology acceptable for a programmer not very experienced in any testing?", I can say that the problems encountered were not so much related to writing the tests as to the configuration of the test classes, because testing each layer of the application requires different settings. The basic features and patterns of TDD were also fully presented.
