# Используем официальный образ OpenJDK
FROM openjdk:23-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл в контейнер
COPY target/MusicHub-0.0.1-SNAPSHOT.jar /app/app.jar

COPY src/main/resources /app/src/main/resources

# Открываем порт
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
