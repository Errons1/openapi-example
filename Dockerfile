FROM azul/zulu-openjdk-alpine:21-latest as builder

# Adds build tools
RUN apk update
RUN apk add --no-cache binutils
RUN apk add --no-cache maven

# Compiles app to .jar
WORKDIR /app
COPY . .
RUN mvn clean package

# Unzips .jar
RUN unzip /app/target/*.jar

# Makes custome JRE based upon modules the .jar files needs
RUN java_deps=$(jdeps \
    --print-module-deps \
    --ignore-missing-deps \
    --recursive --multi-release 21 \
    --class-path="./BOOT-INF/lib/*" \
    --module-path="./BOOT-INF/lib/*" /app/target/*.jar) && \
$JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules $java_deps \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=builder /customjre $JAVA_HOME
COPY --from=builder /app/target/*.jar /app/application.jar
CMD ["java","-jar", "/app/application.jar"]