FROM amazoncorretto:17-alpine-jdk
COPY target/backend-shopapp-0.0.1-SNAPSHOT.jar nicode-shop-products-app.jar
ENTRYPOINT ["java", "-jar", "nicode-shop-products-app.jar"]