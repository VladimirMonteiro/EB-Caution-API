package com.outercode.caution.services.imp;

import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.entities.Load;
import com.outercode.caution.entities.LoadResponsibility;
import com.outercode.caution.repositories.LoadRepository;
import com.outercode.caution.repositories.LoadResponsibilityRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.ILoadService;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoadService implements ILoadService {

    private final LoadRepository loadRepository;
    private final UserRepository userRepository;
    private final LoadResponsibilityRepository loadResponsibilityRepository;

    @Override
    @Transactional
    public LoadResponse create (UUID userId, CreateLoadRequestDTO dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

        var load = new Load();
        load.setPelName(dto.pelName());
        load.setCia(dto.cia());
        load.setActive(true);

        user.addLoad(load);
        loadRepository.save(load);

        userRepository.save(user);

        var loadResponsibility = new LoadResponsibility();
        loadResponsibility.setRole(user.getRole());
        loadResponsibility.setLoad(load);
        loadResponsibility.setStartDate(LocalDateTime.now());

        loadResponsibilityRepository.save(loadResponsibility);

        return new LoadResponse(load.getId(), load.getPelName(), load.getCia(), load.getCreatedAt());
    }
}
