package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.sql.SQLException;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unchecked(){
        Controller controller =new Controller();
        Assertions.assertThatThrownBy(controller::request).isInstanceOf(RuntimeSQLException.class);
    }


    @Test
    void printEx(){
        Controller controller =new Controller();

        try{
            controller.request();
        }catch (Exception e){
//            e.printStackTrace();
            log.info("ex",e);
        }
    }



    static class Controller{
        Service service =new Service();

        public void request() {
            service.logic();
        }
    }



    static class Service{
        Repository repository = new Repository();
        NetworkClient networkClient =new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }

    }

    static class NetworkClient{
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }

    }

    static class Repository{
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) { //예외를 던질 때 기존 예외를 넣어 주어야 한다.
                throw new RuntimeSQLException(e);
//                throw new RuntimeSQLException(); //기존 예외(e) 제외
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }


    }

    static class RuntimeConnectException extends RuntimeException{
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException{
        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }



}
