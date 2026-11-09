java ^
-Dspring.datasource.url=jdbc:hsqldb:file:../db/healthtracker ^
-Dspring.datasource.driverClassName=org.hsqldb.jdbc.JDBCDriver ^
-Dspring.datasource.username=sa ^
-Dspring.datasource.password= ^
-Dlogging.level.root=INFO ^
-jar swing-gui/target/health-tracker-swing-gui-1.10.0.jar
