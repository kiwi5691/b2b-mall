FROM  java:8-jre
MAINTAINER kiwi5691
EXPOSE 8081
COPY docker_dev.sh /usr/bin/docker_dev.sh
RUN chmod +x /usr/bin/docker_dev.sh
CMD nohup sh -c "docker_dev.sh && java -jar /b2b-admin-1.0.jar"