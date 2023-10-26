# Stage 1: Clone and Build
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Clone the latest code from the git repo
RUN apt-get update && \
    apt-get install -y git maven && \
    git clone https://github.com/purvish25/observations-app

# Build the project and create a jar
WORKDIR /app/observations-app
RUN mvn clean package

# Stage 2: Create final image and run
FROM openjdk:17-jdk-slim


# Copy the jar from the previous stage
COPY --from=build /app/observations-app/target/observations-app-*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]