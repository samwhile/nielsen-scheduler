FROM adoptopenjdk/openjdk8

WORKDIR "/usr/app"

COPY build/libs/nielsen-scheduler.jar /usr/app/nielsen-scheduler.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "nielsen-scheduler.jar"]