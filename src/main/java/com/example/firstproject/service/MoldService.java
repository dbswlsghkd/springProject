package com.example.firstproject.service;

import com.example.firstproject.dto.MoldDto;
import com.example.firstproject.dto.PartDto;
import com.example.firstproject.entity.Mold;
import com.example.firstproject.mapper.MoldMapper;
import com.example.firstproject.repository.MoldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoldService {

    @Autowired
    private MoldRepository moldRepository;

    private final MoldMapper moldMapper;

    public Page<Mold> index(Pageable pageable) {
        // return partRepository.findPartBy(pageable);
        log.info("index==========> 여기 들어와" );
        return moldMapper.findMoldAll(pageable);
    }

    public Page<Mold> searchMold(String searchTerm, Pageable pageable) {
        String searchPattern = "%" + searchTerm + "%";
        // return partRepository.findBySearch(searchPattern, pageable);
        log.info("searchParts ==========> 여기 들어와" );
        return moldMapper.findBySearch(searchPattern, pageable);
    }

    @Transactional
    public MoldDto create(MoldDto moldDto) {
        Mold molds = moldMapper.findByMoldCode(moldDto.getM_pcode());

        if (molds == null) {
            Mold mold = Mold.createMold(moldDto);

            Mold createdMold = moldRepository.save(mold);
            return MoldDto.createMoldDto(createdMold);
        }else {
            return null;
        }
    }

    @Transactional
    public MoldDto update(String m_pcode, MoldDto moldDto) {
        Mold target = moldMapper.findByMoldCode(m_pcode);

        if(target == null) {
            return null;
        }else {
            target.patch(moldDto);

            Mold updatedMold = moldRepository.save(target);
            return MoldDto.createMoldDto(updatedMold);
        }
    }
}
