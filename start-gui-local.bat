java ^
-Dspring.datasource.url=jdbc:hsqldb:file:../db/healthtracker ^
-Dspring.datasource.driverClassName=org.hsqldb.jdbc.JDBCDriver ^
-Dspring.datasource.username=sa ^
-Dspring.datasource.password= ^
-Dlogging.level.root=WARN ^
-jar swing-gui/target/health-tracker-swing-gui-1.7.0.jar
