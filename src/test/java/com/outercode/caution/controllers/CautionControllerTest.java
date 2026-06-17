package com.outercode.caution.controllers;

import com.outercode.caution.dto.cautionDTO.CautionResponse;
import com.outercode.caution.dto.cautionDTO.CreateCautionItemRequestDTO;
import com.outercode.caution.dto.cautionDTO.CreateCautionRequestDTO;
import com.outercode.caution.entities.enums.CautionStatus;
import com.outercode.caution.services.ICautionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CautionControllerTest {

    @Mock
    private ICautionService cautionService;

    @InjectMocks
    private CautionController cautionController;

    @Test
    void createShouldReturnCreatedStatusAndBody() {
        UUID userId = UUID.randomUUID();
        UUID loadId = UUID.randomUUID();
        CreateCautionRequestDTO request = new CreateCautionRequestDTO(
                loadId,
                UUID.randomUUID(),
                "Observacao",
                List.of(new CreateCautionItemRequestDTO(UUID.randomUUID(), 1, LocalDateTime.now().plusDays(1)))
        );
        CautionResponse responseBody = new CautionResponse(
                UUID.randomUUID(),
                loadId,
                request.militaryId(),
                "Silva",
                userId,
                CautionStatus.ACTIVE,
                "Observacao",
                LocalDateTime.now(),
                List.of()
        );

        when(cautionService.create(userId, request)).thenReturn(responseBody);

        var response = cautionController.create(userId, request);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertEquals(responseBody, response.getBody());
        verify(cautionService).create(userId, request);
    }

    @Test
    void findAllShouldReturnOkStatusAndBody() {
        UUID userId = UUID.randomUUID();
        List<CautionResponse> responseBody = List.of(
                new CautionResponse(
                        UUID.randomUUID(),
                        null,
                        UUID.randomUUID(),
                        "Silva",
                        userId,
                        CautionStatus.ACTIVE,
                        "Observacao",
                        LocalDateTime.now(),
                        List.of()
                )
        );

        when(cautionService.findAll(userId, 0, 20)).thenReturn(responseBody);

        var response = cautionController.findAll(userId, 0, 20);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(responseBody, response.getBody());
        verify(cautionService).findAll(userId, 0, 20);
    }
}
