# Usa imagem oficial do Java 17 com JDK para compilar seu projeto
FROM openjdk:17-jdk-alpine AS build

# Define que dentro do container vamos trabalhar na pasta /app
WORKDIR /app

# Copia seu pom.xml (arquivo do Maven) para dentro do container
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copia sua pasta src (código fonte) para dentro do container
COPY src ./src

# Dá permissão de execução para o script mvnw (resolve o erro 126)
RUN chmod +x mvnw

# Roda o comando para compilar seu projeto e gerar o arquivo .jar (pulando os testes)
RUN ./mvnw clean package -DskipTests

# Segunda etapa: imagem só para rodar sua aplicação, sem JDK completo
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia o arquivo .jar gerado na primeira etapa para esta imagem
COPY --from=build /app/target/*.jar app.jar

# Diz que o container vai abrir a porta 8080 para receber requisições
EXPOSE 8080

# Diz o comando para rodar a aplicação quando o container iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]
