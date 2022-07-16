# Kuery



## Usability


## Implementation
#### Type Inference
Type inference is quite difficult while working with an ORM. 
The base problem here is how do we map data from the database to the application.
It's only solution is using the factory design pattern. 
This whilst being great does pause an issue when it comes to 
the amount of code required to create an application. 
Causing for redundant or repetitive code that is as usual very cumbersome going against the 
base reason of software development (reducing work done).

Whilst being a great solution for maintainability it's usability isn't as elegant as a final solution.

Annotation processing still remains the best solution for such so far.