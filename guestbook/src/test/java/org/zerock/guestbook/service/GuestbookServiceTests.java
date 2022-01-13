package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {
    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("testTitle")
                .content("testContent")
                .writer("testUser")
                .build();

        System.out.println(service.register(dto));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(31)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = service.getList(pageRequestDTO);

        System.out.println("prev: " + pageResultDTO.isPrev());
        System.out.println("next: " + pageResultDTO.isNext());
        System.out.println("total page: " + pageResultDTO.getTotalPage());

        System.out.println("----- guestbook DTO 시작 -----");
//        for (GuestbookDTO guestbookDTO : pageResultDTO.getDtoList()) {
//            System.out.println(guestbookDTO);
//        }
        pageResultDTO.getDtoList().forEach(System.out::println);
        System.out.println("----- guestbook DTO 끝 -----");

        System.out.println("----- 페이지 목록 시작 -----");
        pageResultDTO.getPageList().forEach(System.out::println);
        System.out.println("----- 페이지 목록 끝 -----");
    }

    @Test
    public void testModify() {
        Long testGno = 305L;
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(testGno)
                .title("testModifyTitle")
                .content("testModifyContent")
                .build();

        System.out.println("GuestbookDTO: " + dto);

        System.out.println("Modifying...");
        service.modify(dto);

        GuestbookDTO modifiedDto = service.read(testGno);
        System.out.println("GuestbookDTO: " + modifiedDto);
    }

    @Test
    public void testRemove() {
        Long gno = 304L;    // 있는 번호
        service.remove(gno);
        System.out.println("1. remove method done");
        System.out.println("1. remove " + (service.read(gno) == null ? "success" : "fail"));

        service.remove(gno);    // 없는 번호
        System.out.println("2. remove method done");
    }
}
