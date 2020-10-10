FROM openjdk

ADD target/api.jar .

CMD ["java", "--enable-preview", "-jar", "-Xmx300m", "-Xss512k", "banco-beer-api.jar"]