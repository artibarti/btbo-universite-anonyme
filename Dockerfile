FROM openjdk:8
ADD btbo-universite-anonyme/target/application.jar application.jar
ADD btbo-universite-anonyme/wait-for-it.sh wait-for-it.sh
EXPOSE 8081
ENTRYPOINT ["./wait-for-it.sh", "database:3306", "-t", "20", "--", "java", "-jar", "application.jar", "com.buildtwicebulldozeonce.universiteanonyme.UniversiteAnonymeApplication"]