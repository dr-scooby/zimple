#FROM openjdk:17.0.1
FROM alpine
#install jdk17 in alpine linux OS
RUN apk add --no-cache java-cacerts openjdk17-jdk

WORKDIR /app

# Copy the jar file from the target folder to the container
COPY target/Zimple-0.0.1-SNAPSHOT.jar /app/zimpleapp.jar

COPY shopping.txt /app

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "zimpleapp.jar"]