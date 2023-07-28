## This is the Java Development Kit Version

FROM eclipse-temurin:17.0.7_7-jdk

# Location of the app
WORKDIR /app

# Find the java file that is created when we run mvn clean package
COPY target/SCTP-SE-MODULE03-JAVA-PROJECTTEAM02-0.0.1.jar harbourBookingSystem-0.0.1.jar

CMD ["java", "-jar", "harbourBookingSystem-0.0.1.jar"]


# mvn clean package will compile your code and also package it. 
# For example, if your pom says the project is a jar, 
# it will create a jar for you when you package it and put it somewhere in the target directory (by default)