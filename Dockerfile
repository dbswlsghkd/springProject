# 베이스 이미지 설정 (JDK 사용)
FROM openjdk:17-jdk-alpine

# 컨테이너 내에 /tmp 디렉터리를 볼륨으로 설정
VOLUME /tmp

# jar 파일 위치
ARG JAR_FILE=build/libs/*.jar

# jar 파일을 컨테이너의 app.jar로 복사
COPY ${JAR_FILE} app.jar

# 컨테이너가 사용할 포트를 설정, 이 경우에는 8081 포트를 사용
EXPOSE 8081

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]