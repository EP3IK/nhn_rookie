package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    // GuestbookDTO List
    private List<DTO> dtoList;

    // 페이지의 전체 번호, 현재 번호, 목록 크기, 시작 번호, 끝 번호
    private int totalPage, page, size, start, end;

    // 이전 버튼 유무, 다음 버튼 유무
    private boolean prev, next;

    // 번호 목록: 위의 int 값을 활용해서 만들어낼 예정
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        page = pageable.getPageNumber();
        size = pageable.getPageSize();

        start = (page / 10) * 10;
        end = start + 10;

        if (totalPage > end) {
            next = true;
        } else {
            next = false;
            end = totalPage;
        }
        start++; page++;
        prev = start > 1;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
