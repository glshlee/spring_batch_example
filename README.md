# spring_batch_example

# build
./gradlew build --exclude-task test

# run
docker-compose -f config/mysql.yml up -d

docker exec -it batch_mysql /bin/bash

mysql -u root -p

java -jar build/libs/batch-0.0.1-SNAPSHOT.jar name=hoon fileName=test.csv

