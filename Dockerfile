FROM maven as build

WORKDIR /app

COPY . .

RUN mvn clean package

FROM openjdk:21-slim

COPY --from=build /app/target/game-hub-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]