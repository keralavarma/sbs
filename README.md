# sbs - Coding test for Southbank
Following details will be helpfull for executing code

## Step to Execute using Maven
First run clean package which will also run the tests 
`mvn clean package`
 
 Run below command to run the application 
`mvn exec:java -Dexec.mainClass="com.southbank.sbs.App" -Dexec.args="t1.json t2.json output.json"`

Where 
t1.json = first file
t2.json = second file
output.json = output file name

## Running Test
`mvn test`

