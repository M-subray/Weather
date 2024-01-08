package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domein.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "insert into memo values(?,?)";
        // 생성엔 update
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    public List<Memo> findAll() {
        String sql = "select * from memo";
        // 조회엔 query
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    /*
    id는 primary Key라 하나씩만 있지만, 자바는 모르기 때문에
    findFist()를 이용해 첫번째것을 가져오게 한다. 그러면
    값이 없을 때를 방지해서 Optional 객체로 변경하라고 권유함.
     */
    public Optional<Memo> findById(int id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
    }

    /*
    jdbc 를 통해 db를 가졍면 ResultSet 형식의 데이터형태를 갖는다.
    {id = 1, text = 'this. is memo~'}  < ResultSet 형식
    이러한 형식을 스프링부트에 만들어둔 Memo 라는 클래스 형식으로
    대입을 시켜야한다. ResultSet 을 이런한 형식으로 대입하게 해주는 기능이
    RowMapper 이다.
     */
    private RowMapper<Memo> memoRowMapper() {
        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }
}
