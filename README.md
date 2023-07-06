# smartnotify
Trabalho de Conclusão de Curso apresentado à Banca examinadora da Pontifícia Universidade Católica de São Paulo, como exigência parcial para obtenção do título de Bacharel em Ciência da Computação, sob a orientação do Professor Doutor Carlos Eduardo de Barros Paes.

## Run the project

1. Make sure you have the Docker installed on your machine. Then, pull the latest mysql image:
    ```
    docker pull mysql
    ```

2. Make sure to fill up the placeholders. Then, run the container:
    ```
    docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD={password} -e MYSQL_DATABASE={database} -p {port}:{port} mysql
    ```

3. To make sure the `mysql-container` is running:
    ```
    docker ps
    ```

4. To execute the Spring Boot application, open a terminal in the root folder of the project and run:
    ```
    ./gradlew bootRun
    ```
   