package com.outercode.caution.services.imp;

import com.outercode.caution.dto.LoadItemDTO.CreateLoadItemRequestDTO;
import com.outercode.caution.dto.LoadItemDTO.LoadItemResponse;
import com.outercode.caution.dto.loadDTO.CreateLoadRequestDTO;
import com.outercode.caution.dto.loadDTO.LoadDetailsResponseDTO;
import com.outercode.caution.dto.loadDTO.LoadResponse;
import com.outercode.caution.entities.Load;
import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.LoadResponsibility;
import com.outercode.caution.mappers.load.LoadMapper;
import com.outercode.caution.repositories.*;
import com.outercode.caution.services.ILoadService;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoadService implements ILoadService {

    private final LoadRepository loadRepository;
    private final LoadItemRepository loadItemRepository;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository;
    private final LoadResponsibilityRepository loadResponsibilityRepository;

    @Override
    @Transactional
    public LoadResponse create(UUID userId, CreateLoadRequestDTO dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado."));

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

    @Override
    @Transactional
    public LoadItemResponse addLoadItem(UUID userId, UUID loadId, UUID materialId, CreateLoadItemRequestDTO dto) {
        var load = loadRepository.findByIdAndUsers_Id(loadId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Carga nao encontrada ou nao pertence ao usuario."));

        var material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ObjectNotFoundException("Material nao encontrado."));

        var loadItem = new LoadItem(load, material, dto.expectedQuantity(), dto.description());
        loadItemRepository.save(loadItem);

        return new LoadItemResponse(
                material.getId(),
                material.getName(),
                loadItem.getExpectedQuantity(),
                loadItem.getDescription()
        );
    }

    @Override
    @Transactional
    public void removeLoadItem(UUID userId, UUID loadId, UUID materialId) {
        loadRepository.findByIdAndUsers_Id(loadId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Carga nao encontrada ou nao pertence ao usuario."));

        var loadItem = loadItemRepository
                .findById_Load_IdAndId_Material_Id(loadId, materialId)
                .orElseThrow(() -> new ObjectNotFoundException("Item da carga nao encontrado."));

        loadItemRepository.delete(loadItem);
    }

    @Override
    public List<LoadResponse> findAll(UUID userId, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("pelName"));
        var loadPage = loadRepository.findByUsers_Id(userId, pageable);

        return loadPage.stream().map(LoadMapper::toLoadResponse).toList();
    }

    @Override
    public LoadDetailsResponseDTO findById(UUID userId, UUID loadId) {
        var load = loadRepository.findByIdAndUsers_Id(loadId, userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario ou carga nao encontrada."));

        var loadItems = loadItemRepository.findById_Load_Id(loadId);

        return LoadMapper.toLoadDetailsResponse(load, loadItems);
    }
}
