FROM openjdk:21

WORKDIR /app


COPY target/Contact_manager_web-0.0.1-SNAPSHOT.jar /app/Contact_manager_web-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar" , "Contact_manager_web-0.0.1-SNAPSHOT.jar"]

