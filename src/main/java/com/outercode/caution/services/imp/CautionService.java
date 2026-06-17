package com.outercode.caution.services.imp;

import com.outercode.caution.dto.cautionDTO.CautionItemResponse;
import com.outercode.caution.dto.cautionDTO.CautionResponse;
import com.outercode.caution.dto.cautionDTO.CreateCautionRequestDTO;
import com.outercode.caution.entities.Caution;
import com.outercode.caution.entities.CautionItem;
import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.enums.CautionItemStatus;
import com.outercode.caution.entities.enums.CautionStatus;
import com.outercode.caution.repositories.CautionRepository;
import com.outercode.caution.repositories.LoadItemRepository;
import com.outercode.caution.repositories.LoadRepository;
import com.outercode.caution.repositories.MilitaryRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.ICautionService;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CautionService implements ICautionService {

    private final CautionRepository cautionRepository;
    private final LoadRepository loadRepository;
    private final LoadItemRepository loadItemRepository;
    private final MilitaryRepository militaryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CautionResponse create(UUID userId, CreateCautionRequestDTO dto) {
        validateUniqueMaterials(dto);

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario nao encontrado."));

        loadRepository.findByIdAndUsers_Id(dto.loadId(), userId)
                .orElseThrow(() -> new ObjectNotFoundException("Carga nao encontrada ou nao pertence ao usuario."));

        var military = militaryRepository.findByIdAndUsers_Id(dto.militaryId(), userId)
                .orElseThrow(() -> new ObjectNotFoundException("Militar nao encontrado ou nao pertence ao usuario."));

        var caution = new Caution();
        caution.setUser(user);
        caution.setMilitary(military);
        caution.setStatus(CautionStatus.ACTIVE);
        caution.setObservations(dto.observations());
        caution.setCautionDate(LocalDateTime.now());

        for (var itemDto : dto.items()) {
            var loadItem = findLoadItem(dto.loadId(), itemDto.materialId());
            validateAvailableStock(loadItem, itemDto.quantity());
            loadItem.setAvailableQuantity(loadItem.getAvailableQuantity() - itemDto.quantity());

            caution.getItems().add(new CautionItem(
                    loadItem.getMaterial(),
                    caution,
                    itemDto.quantity(),
                    CautionItemStatus.ACTIVE,
                    itemDto.deliveryDate()
            ));
        }

        var savedCaution = cautionRepository.save(caution);

        var itemsResponse = savedCaution.getItems().stream()
                .map(item -> new CautionItemResponse(
                        item.getMaterial(),
                        item.getId().getMaterial().getName(),
                        item.getQuantity(),
                        item.getStatus(),
                        item.getDeliveryDate()
                ))
                .toList();

        return new CautionResponse(
                savedCaution.getId(),
                dto.loadId(),
                military.getId(),
                military.getWarName(),
                user.getId(),
                savedCaution.getStatus(),
                savedCaution.getObservations(),
                savedCaution.getCautionDate(),
                itemsResponse
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CautionResponse> findAll(UUID userId, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "cautionDate"));
        var cautions = cautionRepository.findByUser_Id(userId, pageable);

        return cautions.stream().map(this::toResponse).toList();
    }

    private LoadItem findLoadItem(UUID loadId, UUID materialId) {
        return loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, materialId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O material informado nao pertence a carga selecionada."
                ));
    }

    private void validateAvailableStock(LoadItem loadItem, Integer requestedQuantity) {
        if (loadItem.getAvailableQuantity() < requestedQuantity) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantidade indisponivel em estoque para o material informado."
            );
        }
    }

    private void validateUniqueMaterials(CreateCautionRequestDTO dto) {
        var materialIds = new HashSet<UUID>();

        for (var item : dto.items()) {
            if (!materialIds.add(item.materialId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Nao e permitido informar o mesmo material mais de uma vez na cautela."
                );
            }
        }
    }

    private CautionResponse toResponse(Caution caution) {
        var itemsResponse = caution.getItems().stream()
                .map(item -> new CautionItemResponse(
                        item.getMaterial(),
                        item.getId().getMaterial().getName(),
                        item.getQuantity(),
                        item.getStatus(),
                        item.getDeliveryDate()
                ))
                .toList();

        return new CautionResponse(
                caution.getId(),
                null,
                caution.getMilitary().getId(),
                caution.getMilitary().getWarName(),
                caution.getUser().getId(),
                caution.getStatus(),
                caution.getObservations(),
                caution.getCautionDate(),
                itemsResponse
        );
    }
}
