package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.range(1, 301).forEach(i -> {
            String title = String.format("Title - %03d", i);
            String content = String.format("Content - %03d", i);
            String writer = String.format("user%03d", i);

            Guestbook guestbook = Guestbook.builder()
                    .title(title)
                    .content(content)
                    .writer(writer)
                    .build();

            System.out.println(guestbookRepository.save(guestbook));
        });

    }

    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L);    // 존재하는 번호로 테스트

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

//            guestbook.changeContent("executed from updateTest()");

            guestbookRepository.save(guestbook);
        }

    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        // Q도메인 클래스를 가져오면 qGuestbook.title처럼 필드를 변수로 사용 가능
        QGuestbook qGuestbook = QGuestbook.guestbook;

        // SQL WHERE 문에 들어가는 조건을 담는 컨테이너
        // BooleanBuilder implements com.querydsl.core.types.Predicate
        BooleanBuilder builder = new BooleanBuilder();

        // 필드 값에 존재여부, 대소비교 등의 메서드를 결합해서 조건 생성
        BooleanExpression expression = qGuestbook.title.contains("0");

        // 만들어진 조건은 and 또는 or로 빌더와 결합
        builder.and(expression);

        // GuestbookRepository의 부모 인터페이스 QuerydslPredicateExecutor의 findAll 메서드는
        // Predicate와 Pageable을 인자로 받을 수 있다.
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);

    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";
        BooleanExpression exGno = qGuestbook.gno.gt(0L),
                exTitle = qGuestbook.title.contains(keyword),
                exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exTitleOrContent = exTitle.or(exContent);

        builder.and(exGno).and(exTitleOrContent);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);

    }

}
