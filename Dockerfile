FROM openjdk:8-jre-alpine
COPY target/algeria-gate-boot-*-boot.jar /usr/local/app.jar
WORKDIR /usr/local/
# 修改docker时区为东八区，规避应用程序和北京时间相差8小时问题
ENV TZ=Asia/Shanghai
ENV CONFIG_ENCRYPT_KEY=false
CMD ["sh","-c","/aiops.sh&&java -javaagent:/root/APM/aiopsagent-1.8.0.jar -Dpinpoint.licence=$licence -Dpinpoint.agentId=${APP_NAME}_${cluster} -Dpinpoint.applicationName=${APP_NAME}_${cluster} -Dspring.config.encrypt-key=${CONFIG_ENCRYPT_KEY} -jar app.jar"]