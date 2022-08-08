FROM openjdk:8
ENV SPRING_PROFILES_ACTIVE prod
VOLUME /tmp
EXPOSE 9085
ADD ./target/ms-credit-0.0.1-SNAPSHOT.jar ms-credit.jar
ENTRYPOINT ["java","-jar","/ms-credit.jar"]