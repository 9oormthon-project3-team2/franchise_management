# Base Image
FROM openjdk:17-jdk-slim

# 빌드 시 인수로 COLLECTOR_IP 받기
ARG COLLECTOR_IP
ENV COLLECTOR_IP=${COLLECTOR_IP}

# Pinpoint Agent 다운로드 및 설정
ENV PINPOINT_VERSION=3.0.0
RUN wget https://repo1.maven.org/maven2/com/navercorp/pinpoint/pinpoint-agent/${PINPOINT_VERSION}/pinpoint-agent-${PINPOINT_VERSION}.tar.gz \
    && tar -xzf pinpoint-agent-${PINPOINT_VERSION}.tar.gz -C /opt/ \
    && mv /opt/pinpoint-agent-${PINPOINT_VERSION} /opt/pinpoint-agent \
    && rm pinpoint-agent-${PINPOINT_VERSION}.tar.gz

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/friendchise-0.0.1-SNAPSHOT.jar /app/friendchise.jar

# Pinpoint Agent 설정
ENV JAVA_OPTS="-javaagent:/opt/pinpoint-agent/pinpoint-bootstrap-${PINPOINT_VERSION}.jar \
               -Dpinpoint.agentId=friendchiseAgent1 \
               -Dpinpoint.applicationName=friendchise \
               -Dpinpoint.collector.ip=${COLLECTOR_IP}"

# 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar friendchise.jar"]