import org.example.App;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    public static String appTestRun(String input){
        Scanner scanner = new Scanner(input);
//        ByteArrayOutputStream output = TestUtil.setOutPutByteArray();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        System.setOut(printStream);

        App app = new App();
        app.run(scanner);

        return output.toString();
    }

    @Test
    @DisplayName("1단계")
    void t1() {
        String output = appTestRun("""
            종료
            """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n" +
                        "명령) "
        );
    }

    @Test
    @DisplayName("2단계")
    void t2() {
        String output = appTestRun("""
              등록
              현재를 사랑하라.
              작자미상
              종료
              """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n" +
                        "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n" +
                        "명령) "
        );
    }

    @Test
    @DisplayName("3단계")
    void t3() {
        String output = appTestRun("""
            등록
            현재를 사랑하라.
            작자미상
            종료
            """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n"
                        + "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n"
                        + "명령) "
        );
    }

    @Test
    @DisplayName("4단계")
    void t4() {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n"
                        + "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n"
                        + "명령) 명언 : 작가 : 2번 명언이 등록되었습니다.\n"
                        + "명령) "
        );
    }

    @Test
    @DisplayName("5단계")
    void t5() {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n"
                        + "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n"
                        + "명령) 명언 : 작가 : 2번 명언이 등록되었습니다.\n"
                        + "명령) 번호 / 작가 / 명언\n"
                        + "----------------------\n"
                        + "2 / 작자미상 / 과거에 집착하지 마라.\n"
                        + "1 / 작자미상 / 현재를 사랑하라.\n"
                        + "명령) "
        );
    }


    @Test
    @DisplayName("6단계")
    void t6() {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                종료
                """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n"
                        + "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n"
                        + "명령) 명언 : 작가 : 2번 명언이 등록되었습니다.\n"
                        + "명령) 번호 / 작가 / 명언\n"
                        + "----------------------\n"
                        + "2 / 작자미상 / 과거에 집착하지 마라.\n"
                        + "1 / 작자미상 / 현재를 사랑하라.\n"
                        + "명령) 1번 명언이 삭제되었습니다.\n"
                        + "명령) "
        );
    }

    @Test
    @DisplayName("7단계")
    void t7() {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                삭제?id=1
                등록
                미래를 준비하라.
                작자미상
                종료
                """);

        assertThat(output)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.")
                .contains("3번 명언이 등록되었습니다.")
                .doesNotContain("1번 명언이 등록되었습니다.\n명령) ");
    }

    @Test
    @DisplayName("8단계")
    void t8() {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);

        assertThat(output).isEqualTo(
                "== 명언 앱 ==\n"
                        + "명령) 명언 : 작가 : 1번 명언이 등록되었습니다.\n"
                        + "명령) 명언 : 작가 : 2번 명언이 등록되었습니다.\n"
                        + "명령) 1번 명언이 삭제되었습니다.\n"
                        + "명령) 1번 명언은 존재하지 않습니다.\n"
                        + "명령) 3번 명언은 존재하지 않습니다.\n"
                        + "명령) 명언(기존) : 과거에 집착하지 마라.\n"
                        + "명언 : 작가(기존) : 작자미상\n"
                        + "작가 : 명령) 번호 / 작가 / 명언\n"
                        + "----------------------\n"
                        + "2 / 홍길동 / 현재와 자신을 사랑하라.\n"
                        + "명령) "
        );
    }

    @Test
    @DisplayName("9단계")
    void t9() {

        appTestRun("""
            등록
            현재를 사랑하라.
            작자미상
            종료
            """);

        String output = appTestRun("""
            목록
            종료
            """);

        assertThat(output).contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("10단계")
    void t10() throws IOException {
        String output = appTestRun("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                빌드
                종료
                """);


        assertThat(output).contains("data.json 파일의 내용이 갱신되었습니다.");

        assertThat(Files.readString(Path.of("db/wiseSaying/data.json"))).isEqualTo("""
                [
                {
                  "id": 2,
                  "content": "현재와 자신을 사랑하라.",
                  "author": "홍길동"
                }
                ]""");
    }
}