FROM amazoncorretto:11
ENV TZ=Europe/Moscow
COPY target/*.jar explorewithme-statistic.jar
ENTRYPOINT ["java","-jar","/explorewithme-statistic.jar"]
