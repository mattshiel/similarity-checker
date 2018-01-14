# similarity-checker
A Java API that can rapidly compare two large text files by computing their Jaccard Index. This is the project for my third year Object Oriented Programming module in GMIT.

### Contents
1. [How to Run the Application](#howto)
1. [Project Design](#projectdesign)
          
## [How to Run the Application](#howto)
### Clone this repo
```bash
git clone https://github.com/mattshiel/similarity-checker
```
### Navigate to the Project Folder
```bash
cd similarity-checker
```
### Run the JAR:
```bash
 java –cp ./oop.jar ie.gmit.sw.Runner
```
The user is greeted with a menu interface. From here they can provide any two file locations and output the Jaccard Index.

## [Project Design](#projectdesign)

The design of this API follows object oriented design patterns. The goal was to design an scalable API that follows the principles of high cohesion and loose coupling and the single-responsibilty principle. I achieved this with multiple levels of abstraction, encapsulation and composition. Many classes were designed to perform a single role eg. FileShinglizer is a type of shinglizer that can be used to convert text files to shingles. 

The use of interfaces can be seen with Shingleator. This cleary provides the behaviour of the FileShinglizer class and allows for future extension eg. URLShinglizer. Encapsulation of variables is very improtant and demonstarted in every class.

For future extension of the project I would include an abstract class or interface "Parserator" to outline different types of parsers eg. FileParser, URLParser, StringParser and another interace, possibly Hashator, to handle different types of hashing eg. MinHashing, HashCodeHashing 
I would also like to improve the Consumer class, by having a WorkerThread class where the behaviour can be contained. This is neater than declaring runnable threads from with the Consumer run() method.

Issues:
Mainly down to inexperience working with threads and the Producer-Consumer model, the current iteration of this API can process large text files (War And Peace), shinglize and hash them extremely quickly but is only able to produce an accurate Jaccard Index of smaller text files. This seems to be due to synchrnoization issues and the blocking-queue becoming full. I plan to go back and fix these problems, possibly redesigning the Consumer class and adding a Poison class, that would inject a variable into the end of each text file, allowing EOF handling.

