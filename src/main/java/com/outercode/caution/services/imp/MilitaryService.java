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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Military findOrCreateMilitary (CreateMilitaryRequestDTO dto) {
        return militaryRepository.findByCpf(dto.cpf())
                .orElseGet(() -> militaryRepository.save(Military.create(dto)));
    }
}
