package com.outercode.caution.services.imp;

import com.outercode.caution.dto.cautionDTO.CreateCautionItemRequestDTO;
import com.outercode.caution.dto.cautionDTO.CreateCautionRequestDTO;
import com.outercode.caution.entities.Material;
import com.outercode.caution.entities.Military;
import com.outercode.caution.entities.Load;
import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.Caution;
import com.outercode.caution.entities.CautionItem;
import com.outercode.caution.entities.User;
import com.outercode.caution.entities.enums.CautionItemStatus;
import com.outercode.caution.entities.enums.CautionStatus;
import com.outercode.caution.entities.enums.Role;
import com.outercode.caution.repositories.CautionRepository;
import com.outercode.caution.repositories.LoadItemRepository;
import com.outercode.caution.repositories.LoadRepository;
import com.outercode.caution.repositories.MilitaryRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CautionServiceTest {

    @Mock
    private CautionRepository cautionRepository;

    @Mock
    private LoadRepository loadRepository;

    @Mock
    private LoadItemRepository loadItemRepository;

    @Mock
    private MilitaryRepository militaryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CautionService cautionService;

    @Test
    void createShouldPersistCautionWithActiveStatusAndItems() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();
        UUID firstMaterialId = UUID.randomUUID();
        UUID secondMaterialId = UUID.randomUUID();

        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        user.setId(userId);

        Military military = Military.builder()
                .id(militaryId)
                .warName("Silva")
                .email("silva@example.com")
                .cpf("12345678900")
                .phone("11999999999")
                .cia("1 CIA")
                .pel("1 Pel")
                .grad("Cb")
                .build();

        Material firstMaterial = new Material();
        firstMaterial.setId(firstMaterialId);
        firstMaterial.setName("Fuzil");

        Material secondMaterial = new Material();
        secondMaterial.setId(secondMaterialId);
        secondMaterial.setName("Capacete");

        Load load = new Load();
        load.setId(loadId);

        LoadItem firstLoadItem = new LoadItem(load, firstMaterial, 5, "Reserva");
        LoadItem secondLoadItem = new LoadItem(load, secondMaterial, 3, "Protecao");

        CreateCautionRequestDTO dto = new CreateCautionRequestDTO(
                loadId,
                militaryId,
                "Uso em servico",
                List.of(
                        new CreateCautionItemRequestDTO(firstMaterialId, 2, LocalDateTime.now().plusDays(1)),
                        new CreateCautionItemRequestDTO(secondMaterialId, 1, LocalDateTime.now().plusDays(2))
                )
        );

        ArgumentCaptor<com.outercode.caution.entities.Caution> cautionCaptor =
                ArgumentCaptor.forClass(com.outercode.caution.entities.Caution.class);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(load));
        when(militaryRepository.findByIdAndUsers_Id(militaryId, userId)).thenReturn(Optional.of(military));
        when(loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, firstMaterialId)).thenReturn(Optional.of(firstLoadItem));
        when(loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, secondMaterialId)).thenReturn(Optional.of(secondLoadItem));
        when(cautionRepository.save(any(com.outercode.caution.entities.Caution.class))).thenAnswer(invocation -> {
            var caution = invocation.getArgument(0, com.outercode.caution.entities.Caution.class);
            caution.setId(UUID.randomUUID());
            return caution;
        });

        var response = cautionService.create(userId, dto);

        verify(cautionRepository).save(cautionCaptor.capture());
        var savedCaution = cautionCaptor.getValue();

        assertEquals(CautionStatus.ACTIVE, savedCaution.getStatus());
        assertEquals("Uso em servico", savedCaution.getObservations());
        assertEquals(2, savedCaution.getItems().size());
        assertTrue(savedCaution.getItems().stream().allMatch(item -> item.getStatus() == CautionItemStatus.ACTIVE));
        assertEquals(3, firstLoadItem.getAvailableQuantity());
        assertEquals(2, secondLoadItem.getAvailableQuantity());
        assertEquals(loadId, response.loadId());
        assertEquals(militaryId, response.militaryId());
        assertEquals(2, response.items().size());
        assertEquals("Fuzil", response.items().getFirst().materialName());
    }

    @Test
    void createShouldThrowWhenMaterialIsDuplicated() {
        UUID materialId = UUID.randomUUID();
        CreateCautionRequestDTO dto = new CreateCautionRequestDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                null,
                List.of(
                        new CreateCautionItemRequestDTO(materialId, 1, LocalDateTime.now().plusDays(1)),
                        new CreateCautionItemRequestDTO(materialId, 2, LocalDateTime.now().plusDays(2))
                )
        );

        assertThrows(ResponseStatusException.class, () -> cautionService.create(UUID.randomUUID(), dto));
    }

    @Test
    void createShouldThrowWhenMilitaryDoesNotBelongToUser() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();
        CreateCautionRequestDTO dto = new CreateCautionRequestDTO(
                loadId,
                militaryId,
                null,
                List.of(new CreateCautionItemRequestDTO(UUID.randomUUID(), 1, LocalDateTime.now().plusDays(1)))
        );

        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(new Load()));
        when(militaryRepository.findByIdAndUsers_Id(militaryId, userId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> cautionService.create(userId, dto));
    }

    @Test
    void createShouldThrowWhenRequestedQuantityIsGreaterThanAvailableStock() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        user.setId(userId);

        Military military = Military.builder()
                .id(militaryId)
                .warName("Silva")
                .email("silva@example.com")
                .cpf("12345678900")
                .phone("11999999999")
                .cia("1 CIA")
                .pel("1 Pel")
                .grad("Cb")
                .build();

        Material material = new Material();
        material.setId(materialId);
        material.setName("Fuzil");

        Load load = new Load();
        load.setId(loadId);

        LoadItem loadItem = new LoadItem(load, material, 2, "Reserva");

        CreateCautionRequestDTO dto = new CreateCautionRequestDTO(
                loadId,
                militaryId,
                null,
                List.of(new CreateCautionItemRequestDTO(materialId, 3, LocalDateTime.now().plusDays(1)))
        );

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(load));
        when(militaryRepository.findByIdAndUsers_Id(militaryId, userId)).thenReturn(Optional.of(military));
        when(loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, materialId)).thenReturn(Optional.of(loadItem));

        assertThrows(ResponseStatusException.class, () -> cautionService.create(userId, dto));
    }

    @Test
    void findAllShouldReturnPaginatedMappedCautions() {
        UUID userId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();
        UUID cautionId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        user.setId(userId);

        Military military = Military.builder()
                .id(militaryId)
                .warName("Silva")
                .email("silva@example.com")
                .cpf("12345678900")
                .phone("11999999999")
                .cia("1 CIA")
                .pel("1 Pel")
                .grad("Cb")
                .build();

        Material material = new Material();
        material.setId(materialId);
        material.setName("Fuzil");

        Caution caution = new Caution();
        caution.setId(cautionId);
        caution.setUser(user);
        caution.setMilitary(military);
        caution.setStatus(CautionStatus.ACTIVE);
        caution.setObservations("Uso em servico");
        caution.setCautionDate(LocalDateTime.now());

        CautionItem cautionItem = new CautionItem(
                material,
                caution,
                2,
                CautionItemStatus.ACTIVE,
                LocalDateTime.now().plusDays(1)
        );
        caution.setItems(List.of(cautionItem));

        when(cautionRepository.findByUser_Id(any(UUID.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(List.of(caution));

        var response = cautionService.findAll(userId, 0, 20);

        assertEquals(1, response.size());
        assertEquals(cautionId, response.getFirst().cautionId());
        assertEquals(militaryId, response.getFirst().militaryId());
        assertEquals("Silva", response.getFirst().militaryWarName());
        assertEquals(userId, response.getFirst().userId());
        assertEquals(CautionStatus.ACTIVE, response.getFirst().status());
        assertEquals("Uso em servico", response.getFirst().observations());
        assertEquals(1, response.getFirst().items().size());
        assertEquals("Fuzil", response.getFirst().items().getFirst().materialName());
        assertEquals(null, response.getFirst().loadId());
    }
}
