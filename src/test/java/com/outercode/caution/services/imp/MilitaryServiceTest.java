package com.outercode.caution.services.imp;

import com.outercode.caution.dto.militaryDTO.CreateMilitaryRequestDTO;
import com.outercode.caution.entities.Military;
import com.outercode.caution.entities.User;
import com.outercode.caution.entities.enums.MilitaryStatus;
import com.outercode.caution.entities.enums.Role;
import com.outercode.caution.repositories.MilitaryRepository;
import com.outercode.caution.repositories.UserRepository;
import com.outercode.caution.services.imp.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MilitaryServiceTest {

    @Mock
    private MilitaryRepository militaryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MilitaryService militaryService;

    @Test
    void createShouldPersistMilitaryWhenCpfDoesNotExist() {
        UUID userId = UUID.randomUUID();
        CreateMilitaryRequestDTO dto = new CreateMilitaryRequestDTO(
                "Silva",
                "52998224725",
                "silva@example.com",
                "11999999999",
                "1 CIA",
                "1 Pel",
                "Sgt",
                MilitaryStatus.ACTIVE
        );
        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        Military savedMilitary = Military.create(dto);
        savedMilitary.setId(UUID.randomUUID());

        when(militaryRepository.findByCpf(dto.cpf())).thenReturn(Optional.empty());
        when(militaryRepository.save(any(Military.class))).thenReturn(savedMilitary);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var response = militaryService.create(userId, dto);

        assertEquals(savedMilitary.getId(), response.id());
        assertEquals("Silva", response.warName());
        assertEquals("silva@example.com", response.email());
        assertTrue(user.getMilitaries().contains(savedMilitary));
        assertTrue(savedMilitary.getUsers().contains(user));
        verify(militaryRepository).save(any(Military.class));
    }

    @Test
    void createShouldReuseExistingMilitaryWhenCpfAlreadyExists() {
        UUID userId = UUID.randomUUID();
        CreateMilitaryRequestDTO dto = new CreateMilitaryRequestDTO(
                "Silva",
                "52998224725",
                "silva@example.com",
                "11999999999",
                "1 CIA",
                "1 Pel",
                "Sgt",
                MilitaryStatus.ACTIVE
        );
        User user = new User("Admin", "admin@example.com", "secret", Role.ARMORER, "Sgt");
        Military existingMilitary = Military.builder()
                .id(UUID.randomUUID())
                .warName("Silva")
                .cpf(dto.cpf())
                .email(dto.email())
                .phone(dto.phone())
                .cia(dto.cia())
                .pel(dto.pel())
                .grad(dto.grad())
                .status(dto.status())
                .build();

        when(militaryRepository.findByCpf(dto.cpf())).thenReturn(Optional.of(existingMilitary));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        var response = militaryService.create(userId, dto);

        assertEquals(existingMilitary.getId(), response.id());
        assertTrue(user.getMilitaries().contains(existingMilitary));
        verify(militaryRepository, never()).save(any(Military.class));
    }

    @Test
    void createShouldThrowWhenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        CreateMilitaryRequestDTO dto = new CreateMilitaryRequestDTO(
                "Silva",
                "52998224725",
                "silva@example.com",
                "11999999999",
                "1 CIA",
                "1 Pel",
                "Sgt",
                MilitaryStatus.ACTIVE
        );

        when(militaryRepository.findByCpf(dto.cpf())).thenReturn(Optional.of(Military.create(dto)));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> militaryService.create(userId, dto));
    }

    @Test
    void findAllShouldRequestSortedPageAndMapResults() {
        UUID userId = UUID.randomUUID();
        Military first = Military.builder()
                .id(UUID.randomUUID())
                .warName("Alfa")
                .email("alfa@example.com")
                .grad("Cb")
                .cia("1 CIA")
                .pel("1 Pel")
                .phone("111111111")
                .status(MilitaryStatus.ACTIVE)
                .build();
        Military second = Military.builder()
                .id(UUID.randomUUID())
                .warName("Bravo")
                .email("bravo@example.com")
                .grad("Sgt")
                .cia("2 CIA")
                .pel("2 Pel")
                .phone("222222222")
                .status(MilitaryStatus.RESERVE)
                .build();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        when(militaryRepository.findByUsers_Id(org.mockito.ArgumentMatchers.eq(userId), pageableCaptor.capture()))
                .thenReturn(List.of(first, second));

        var response = militaryService.findAll(userId, 2, 5);

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(2, response.size());
        assertEquals("Alfa", response.getFirst().warName());
        assertEquals(2, pageable.getPageNumber());
        assertEquals(5, pageable.getPageSize());
        assertEquals(Sort.by("warName"), pageable.getSort());
    }

    @Test
    void findByIdShouldReturnMilitaryFromRepository() {
        UUID userId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();
        Military military = Military.builder()
                .id(militaryId)
                .warName("Silva")
                .email("silva@example.com")
                .grad("Sgt")
                .cia("1 CIA")
                .pel("1 Pel")
                .phone("11999999999")
                .status(MilitaryStatus.ACTIVE)
                .build();

        when(militaryRepository.findByIdAndUsers_Id(militaryId, userId)).thenReturn(Optional.of(military));

        var response = militaryService.findById(userId, militaryId);

        assertEquals(militaryId, response.id());
        assertEquals("Silva", response.warName());
    }

    @Test
    void findByIdShouldThrowWhenMilitaryDoesNotBelongToUser() {
        UUID userId = UUID.randomUUID();
        UUID militaryId = UUID.randomUUID();

        when(militaryRepository.findByIdAndUsers_Id(militaryId, userId)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> militaryService.findById(userId, militaryId));
    }
}
