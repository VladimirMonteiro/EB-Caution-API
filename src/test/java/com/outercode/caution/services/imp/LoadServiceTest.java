package com.outercode.caution.services.imp;

import com.outercode.caution.dto.LoadItemDTO.CreateLoadItemRequestDTO;
import com.outercode.caution.dto.LoadItemDTO.UpdateLoadItemRequestDTO;
import com.outercode.caution.entities.Load;
import com.outercode.caution.entities.LoadItem;
import com.outercode.caution.entities.Material;
import com.outercode.caution.repositories.LoadItemRepository;
import com.outercode.caution.repositories.LoadRepository;
import com.outercode.caution.repositories.LoadResponsibilityRepository;
import com.outercode.caution.repositories.MaterialRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadServiceTest {

    @Mock
    private LoadRepository loadRepository;

    @Mock
    private LoadItemRepository loadItemRepository;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoadResponsibilityRepository loadResponsibilityRepository;

    @InjectMocks
    private LoadService loadService;

    @Test
    void addLoadItemShouldStartAvailableQuantityWithExpectedQuantity() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        Load load = new Load();
        load.setId(loadId);

        Material material = new Material();
        material.setId(materialId);
        material.setName("Fuzil");

        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(load));
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));
        when(loadItemRepository.save(any(LoadItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = loadService.addLoadItem(userId, loadId, materialId, new CreateLoadItemRequestDTO(10, "Reserva"));

        assertEquals(10, response.expectedQuantity());
        assertEquals(10, response.availableQuantity());
    }

    @Test
    void updateLoadItemShouldRecalculateAvailableQuantityPreservingCautionedQuantity() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        Load load = new Load();
        load.setId(loadId);

        Material material = new Material();
        material.setId(materialId);
        material.setName("Fuzil");

        LoadItem loadItem = new LoadItem(load, material, 10, "Reserva");
        loadItem.setAvailableQuantity(6);

        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(load));
        when(loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, materialId)).thenReturn(Optional.of(loadItem));
        when(loadItemRepository.save(any(LoadItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = loadService.updateLoadItem(userId, loadId, materialId, new UpdateLoadItemRequestDTO(12, "Atualizado"));

        assertEquals(12, response.expectedQuantity());
        assertEquals(8, response.availableQuantity());
        assertEquals("Atualizado", response.description());
    }

    @Test
    void updateLoadItemShouldThrowWhenNewExpectedQuantityIsLessThanAlreadyCautioned() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        Load load = new Load();
        load.setId(loadId);

        Material material = new Material();
        material.setId(materialId);

        LoadItem loadItem = new LoadItem(load, material, 10, "Reserva");
        loadItem.setAvailableQuantity(6);

        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.of(load));
        when(loadItemRepository.findById_Load_IdAndId_Material_Id(loadId, materialId)).thenReturn(Optional.of(loadItem));

        assertThrows(ResponseStatusException.class,
                () -> loadService.updateLoadItem(userId, loadId, materialId, new UpdateLoadItemRequestDTO(3, null)));
    }

    @Test
    void addLoadItemShouldThrowWhenLoadDoesNotBelongToUser() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        when(loadRepository.findByIdAndUsers_Id(loadId, userId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class,
                () -> loadService.addLoadItem(userId, loadId, materialId, new CreateLoadItemRequestDTO(1, null)));
    }

    @Test
    void findByIdShouldReturnLoadWithItemsFetchedFromRepository() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        UUID materialId = UUID.randomUUID();

        Load load = new Load();
        load.setId(loadId);
        load.setPelName("PEL");
        load.setCia("CIA");

        Material material = new Material();
        material.setId(materialId);
        material.setName("Fuzil");

        LoadItem loadItem = new LoadItem(load, material, 10, "Reserva");
        load.setItems(List.of(loadItem));

        when(loadRepository.findByIdWithItems(loadId, userId)).thenReturn(Optional.of(load));

        var response = loadService.findById(userId, loadId);

        assertEquals(loadId, response.loadId());
        assertEquals(1, response.items().size());
        assertEquals(materialId, response.items().getFirst().materialId());
    }
}
