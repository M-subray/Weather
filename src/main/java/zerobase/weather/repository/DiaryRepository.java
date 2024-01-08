package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domein.Diary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    List<Diary> findAllByDate(LocalDate date);

    List<Diary> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    // 일기를 수정할건데, 같은 날짜여도 일기가 여러개일 수 있기 때문에 가장 첫번째 일기 수정을 위해
    // getFirstByDate로 함수를 만든다. (query에서 * 뒤에 limite(1) ? 해주는 거랑 같다(첫번째 데이터 얻기 위해))
    Diary getFirstByDate(LocalDate date);

    void deleteAllByDate(LocalDate date);
}
