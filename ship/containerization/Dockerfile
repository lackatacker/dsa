# https://spring.io/guides/topicals/spring-boot-docker/
# Notice that the base image in the example above is openjdk:8-jdk-alpine. 
# The alpine images are smaller than the standard openjdk library images from Dockerhub. 
# There is no official alpine image for Java 11 yet (AdoptOpenJDK had one for a while but 
# it no longer appears on their Dockerhub page). 
# You can also save about 20MB in the base image by using the "jre" label instead of "jdk". 
# Not all apps work with a JRE (as opposed to a JDK), but most do, and indeed some organizations 
# enforce a rule that every app has to because of the risk of misuse of some of the JDK features (like compilation).
#
FROM openjdk:8-jre-alpine
#
# Just as in classic VM-deployments, processes should not be run with root permissions.
#Instead the image should contain a non-root user that runs the app.
# In a Dockerfile, this can be achieved by adding another layer that adds a (system) user and group,
# then set it as the current user (instead of the default, root):
RUN addgroup -S spring-boot && adduser -S spring-boot -G spring-boot
USER spring-boot
#
VOLUME /tmp
#
# Once you have chosen a build system, you don’t need the ARG 
# you can just hard code the jar location. E.g. for Maven:
#
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
#
#
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
