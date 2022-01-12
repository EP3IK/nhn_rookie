package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

@Service
@Log4j2
// 의존성 자동 주입: 이거 없으면 GuestbookService 인터페이스에 GuestbookRepository 인스턴스 없다고 에러.
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {
    // 반드시 final로 선언: const로 만들어서 바꿔치기를 못하게?
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("----- DTO 시작 -----");
        log.info(dto);
        log.info("----- DTO 끝 -----");

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        guestbookRepository.save(entity);

        // 등록 후에 자동으로 생성되는 guestbook의 gno를 리턴
        return entity.getGno();
    }
}
