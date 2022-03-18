# spring_batch_example

# build
./gradlew build --exclude-task test

# run
docker-compose -f config/mysql.yml up -d

docker exec -it batch_mysql /bin/bash

mysql -u root -p

ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password by 'password';

FLUSH PRIVILEGES;

java -jar build/libs/batch-0.0.1-SNAPSHOT.jar name=hoon fileName=test.csv

