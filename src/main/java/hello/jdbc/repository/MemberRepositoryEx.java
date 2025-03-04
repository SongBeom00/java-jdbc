package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

/**
 * SQLException 은 jdbc 에 종속적인 기술이다
 */

public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException ;
}