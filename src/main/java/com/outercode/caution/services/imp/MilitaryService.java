package com.outercode.caution.services.imp;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.dto.militaryDTO.MilitaryResponse;
import com.outercode.caution.entities.Military;
import com.outercode.caution.mappers.military.MilitaryMapper;
import com.outercode.caution.repositories.MilitaryRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.IMilitaryService;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MilitaryService implements IMilitaryService {

    private final MilitaryRepository militaryRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public MilitaryResponse create(CreateMilitaryRequestDTO dto) {

        var military = findOrCreateMilitary(dto);

        var user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

        user.addMilitary(military);

        return MilitaryMapper.toMilitaryResponse(military);
    }

    @Override
    public List<MilitaryResponse> findAll(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("warName"));

        var pageMilitary = militaryRepository.findByUsers_Id(userId, pageable);

        return pageMilitary.stream().map(MilitaryMapper::toMilitaryResponse).toList();
    }

    private Military findOrCreateMilitary (CreateMilitaryRequestDTO dto) {
        return militaryRepository.findByCpf(dto.cpf())
                .orElseGet(() -> militaryRepository.save(Military.create(dto)));
    }
}