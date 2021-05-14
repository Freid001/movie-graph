FROM openjdk:11-jre-slim

COPY build/libs/movie-graph-*.jar /app.jar
COPY entrypoint.sh entrypoint.sh

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ENTRYPOINT ["/bin/sh", "./entrypoint.sh"]

EXPOSE 9000