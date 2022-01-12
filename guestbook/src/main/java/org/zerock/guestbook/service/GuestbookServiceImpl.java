package org.zerock.guestbook.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

@Service
@Log4j2
public class GuestbookServiceImpl implements GuestbookService {
    @Override
    public Long register(GuestbookDTO dto) {
        log.info("----- DTO 시작 -----");
        log.info(dto);
        log.info("----- DTO 끝 -----");

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        // 등록 후에 자동으로 생성되는 guestbook의 gno를 리턴
        return null;
    }
}
