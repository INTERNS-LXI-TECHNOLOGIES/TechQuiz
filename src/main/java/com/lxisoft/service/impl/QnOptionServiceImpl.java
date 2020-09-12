package com.lxisoft.service.impl;

import com.lxisoft.service.QnOptionService;
import com.lxisoft.domain.QnOption;
import com.lxisoft.repository.QnOptionRepository;
import com.lxisoft.service.dto.QnOptionDTO;
import com.lxisoft.service.mapper.QnOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link QnOption}.
 */
@Service
@Transactional
public class QnOptionServiceImpl implements QnOptionService {

    private final Logger log = LoggerFactory.getLogger(QnOptionServiceImpl.class);

    private final QnOptionRepository qnOptionRepository;

    private final QnOptionMapper qnOptionMapper;

    public QnOptionServiceImpl(QnOptionRepository qnOptionRepository, QnOptionMapper qnOptionMapper) {
        this.qnOptionRepository = qnOptionRepository;
        this.qnOptionMapper = qnOptionMapper;
    }

    @Override
    public QnOptionDTO save(QnOptionDTO qnOptionDTO) {
        log.debug("Request to save QnOption : {}", qnOptionDTO);
        QnOption qnOption = qnOptionMapper.toEntity(qnOptionDTO);
        qnOption = qnOptionRepository.save(qnOption);
        return qnOptionMapper.toDto(qnOption);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QnOptionDTO> findAll() {
        log.debug("Request to get all QnOptions");
        return qnOptionRepository.findAll().stream()
            .map(qnOptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QnOptionDTO> findOne(Long id) {
        log.debug("Request to get QnOption : {}", id);
        return qnOptionRepository.findById(id)
            .map(qnOptionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QnOption : {}", id);
        qnOptionRepository.deleteById(id);
    }
}
