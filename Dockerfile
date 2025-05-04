# this file does not work, as the new spring boot extract command seems to
# be not working as expected, or I have to take more time learning it
FROM eclipse-temurin:17 AS builder
WORKDIR workspace
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} catalog-service.jar

# Specify a destination directory for extraction (e.g., /workspace/extracted)
RUN java -Djarmode=tools -jar catalog-service.jar extract --layers --launcher --destination=/workspace/extracted

RUN ls -al /workspace/extracted/


FROM eclipse-temurin:17
RUN useradd spring
USER spring
WORKDIR workspace

# Copying the extracted layers from the builder stage
# The source path is /<WORKDIR>/<destination_in_extract>/<layer_name>/
COPY --from=builder /workspace/extracted/dependencies/ ./
COPY --from=builder /workspace/extracted/spring-boot-loader/ ./
# Make sure to include snapshot-dependencies if your project has them
COPY --from=builder /workspace/extracted/snapshot-dependencies/ ./
COPY --from=builder /workspace/extracted/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]