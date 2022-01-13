package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);

        if (result.isPresent()) {
            GuestbookDTO dto = entityToDto(result.get());
            return dto;
        } else {
            return null;
        }
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = guestbookRepository.findById(dto.getGno());

        if (result.isPresent()) {
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            guestbookRepository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        // deleteById 는 알아서 어련히 select 문부터 시작하고 있는지 없는지 체크해주지 않는다!
        // 없는 gno 집어넣으면 에러
//        guestbookRepository.deleteById(gno);

        // 없는 gno 집어넣을 가능성이 있으면 아래의 코드 적용
        Optional<Guestbook> result = guestbookRepository.findById(gno);
        if (result.isPresent()) {
            guestbookRepository.deleteById(gno);
        }
    }
}
