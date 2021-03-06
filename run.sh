#!/usr/bin/env bash

git_pull()
{
    if [ ! -d ".git" ]; then
        git init .
        git remote add origin https://github.com/artibarti/btbo-universite-anonyme.git
    fi
    git pull origin ${1:-$(git rev-parse --abbrev-ref HEAD)}
}

maven_package()
{
    mvn clean
    mvn clean package -Dmaven.test.skip=true
}

docker-machine start &

git_pull &&
maven_package &

wait

docker-compose down &
docker-compose build --no-cache &
wait
docker-compose up