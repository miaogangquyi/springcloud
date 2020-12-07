
package com.mogo.service;

import com.mogo.domain.entity.Dict;
import com.mogo.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import com.mogo.repository.DictRepository;
import com.mogo.service.DictService;
import com.mogo.domain.dto.DictDto;
import com.mogo.domain.qc.DictQueryCriteria;
import com.mogo.domain.mapstruct.DictMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.mogo.util.PageUtil;
import com.mogo.util.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @description 服务实现
* @author miaogang
* @date 2020-12-07
**/
@Service
@RequiredArgsConstructor
public class DictService {

private final DictRepository dictRepository;
private final DictMapper dictMapper;

public Map<String,Object> queryAll(DictQueryCriteria criteria, Pageable pageable){
Page<Dict> page = dictRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
return PageUtil.toPage(page.map(dictMapper::toDto));
}

public List<DictDto> queryAll(DictQueryCriteria criteria){
    return dictMapper.toDto(dictRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Transactional
    public DictDto findById(Long dictId) {
    Dict dict = dictRepository.findById(dictId).orElseGet(Dict::new);
    ValidationUtil.isNull(dict,"dictId",dictId);
    return dictMapper.toDto(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    public DictDto create(Dict resources) {
    return dictMapper.toDto(dictRepository.save(resources));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
    Dict dict = dictRepository.findById(resources.getDictId()).orElseGet(Dict::new);
    ValidationUtil.isNull( dict,"dictId",resources.getDictId());
    dict.copy(resources);
    dictRepository.save(dict);
    }

    public void deleteAll(Long[] ids) {
    for (Long dictId : ids) {
    dictRepository.deleteById(dictId);
    }
    }

    }
