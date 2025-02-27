package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @Test
    void driverManager() throws SQLException {
        //DriverManager 는 커넥션을 획득할 때마다 URL, USERNAME, PASSWORD 같은 파라미터를 계속 전달해야 합니다.
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD); //설정과 사용이 같이 공존
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection={}, class={}",con1,con1.getClass());
        log.info("connection={}, class={}",con2,con2.getClass());
    }


    @Test
    void dataSourceDriverManager() throws SQLException {
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD); //설정과 사용의 분리
        useDataSource(dataSource);

    }


    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }


    private void useDataSource(DataSource dataSource) throws SQLException { //인터페이스를 통해 들고옵니다.
        Connection con1 = dataSource.getConnection(); // 데이터 소스를 사용하면 getConnection() 만 호출하면 됩니다.
        Connection con2 = dataSource.getConnection();
        log.info("connection={}, class={}",con1,con1.getClass());
        log.info("connection={}, class={}",con2,con2.getClass());
    }

}
