# Instruction
1. Build docker image
    ```shell
    docker buildx build -t billing-service:0.0.1 .
    ```
2. Add tag to image
    ```shell
    docker tag billing-service:0.0.1 otuslearning/billing-service:0.0.1
    ```
3. Push image
    ```shell
    docker push otuslearning/billing-service:0.0.1
    ```