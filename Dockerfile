#FROM openjdk:17.0.1
FROM alpine
#install jdk17 in alpine linux OS
RUN apk add --no-cache java-cacerts openjdk17-jdk

# update the Alpine
RUN apk update
RUN apk upgrade

# install the curl
RUN apk --no-cache add curl

WORKDIR /app

# Copy the jar file from the target folder to the container
COPY target/Zimple-0.0.1-SNAPSHOT.jar /app/zimpleapp.jar

COPY shopping.txt /app

# Expose port 2000 to the outside world
EXPOSE 2000

# Run the jar file when the container starts
ENTRYPOINT ["java", "-jar", "zimpleapp.jar"]