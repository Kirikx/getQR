FROM openjdk:11-jre-slim as builder
ARG JAR_FILE=build/libs/*.jar
COPY $JAR_FILE app.jar
RUN java -Djarmode=layertools -jar app.jar extract --destination /extract/

FROM openjdk:11-jre-slim
ARG APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=builder extract/dependencies/ $APP_HOME/
COPY --from=builder extract/spring-boot-loader/ $APP_HOME/
COPY --from=builder extract/snapshot-dependencies/ $APP_HOME/
COPY --from=builder extract/application/ $APP_HOME/
#RUN apt-get update && apt-get install -y libfreetype6

EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]