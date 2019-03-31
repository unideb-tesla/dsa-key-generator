# DSA Key Generator
A small command line application for creating and saving public and private keys for digital signature, implemented in Java. The program will produce 1024 bit keys for the DSA algorithm.

To compile and run the project, read the documentation below:
## Requirements
* Apache Maven 3
* Java 8

## Compile
`mvn clean package`

## Run the application
You can run the application simply with the following command:

`java -jar target/dsa-key-generator-1.0-SNAPSHOT-jar-with-dependencies.jar`

You can also specify the location of the output folder with a single command line argument, like:

`java -jar target/dsa-key-generator-1.0-SNAPSHOT-jar-with-dependencies.jar custom-output-folder/`