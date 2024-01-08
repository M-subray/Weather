package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domein.Diary;
import zerobase.weather.error.InvalidDate;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation(value = "일기 저장"
            , notes = "POST를 이용해 텍스트와 날씨를 통해 DB에 해당 날짜의 일기를 저장할 수 있습니다.")
    // 저장할 때 포스트 맵핑
    @PostMapping ("/create/diary")  // 브라우저 테스트는 get 요청으로 인식 돼서 post 요청 테스트엔 부적합하다.
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "저장할 날짜를 입력(연-월-일)", example = "2024-01-01")
                     LocalDate date, @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @ApiOperation(value = "일기 불러오기", notes = "GET을 통해 해당 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                          @ApiParam(value = "불러올 날짜를 입력(연-월-일", example = "2024-01-01")
                          LocalDate date) {
//        if (date.isAfter(LocalDate.ofYearDay(2023, 1))) {
//            throw new InvalidDate();
//        }
        return diaryService.readDiary(date);
    }

    @ApiOperation(value = "선택한 기간의 모든 일기 불러오기", notes = "GET을 통해 해당 기간의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "조회 기간의 시작 날짜", example = "2024-01-01") LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                            @ApiParam(value = "조회 기간의 마지막 날짜", example = "2024-12-31") LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @ApiOperation(value = "일기 수정하기", notes = "GET을 통해 해당 날짜의 첫 번째 일기 데이터를 수정합니다.")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "수정할 날짜를 입력(연-월-일)", example = "2024-01-01")
                     LocalDate date, @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    @ApiOperation(value = "일기 삭제하기", notes = "DELETE를 통해 해당 날짜의 모든 일기 데이터를 삭제합니다.")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                     @ApiParam(value = "삭제할 날짜를 입력(연-월-일)", example = "2024-01-01")
                     LocalDate date ) {
        diaryService.deleteDiary(date);
    }
}
