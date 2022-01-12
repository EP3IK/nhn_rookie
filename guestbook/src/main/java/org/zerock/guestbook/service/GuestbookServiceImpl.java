package org.zerock.guestbook.service;

import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;

@Service
public class GuestbookServiceImpl implements GuestbookService {
    @Override
    public Long register(GuestbookDTO dto) {
        // 등록 후에 자동으로 생성되는 guestbook의 gno를 리턴
        return null;
    }
}
