package org.zerock.guestbook.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.entity.Guestbook;

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

            guestbook.changeContent("executed from updateTest()");

            guestbookRepository.save(guestbook);
        }

    }
}
