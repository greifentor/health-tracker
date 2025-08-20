java \
-Dspring.datasource.url=jdbc:mariadb://localhost:3306/healthtracker \
-Dspring.datasource.driverClassName=org.mariadb.jdbc.Driver \
-Dspring.datasource.username=healthtracker \
-Dspring.datasource.password=password \
-Dlogging.level.root=WARN \
-jar swing-gui/target/health-tracker-swing-gui-0.3.0.jar
