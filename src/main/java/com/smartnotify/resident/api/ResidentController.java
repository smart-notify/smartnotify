package com.smartnotify.resident.api;

import com.smartnotify.condominium.model.Condominium;
import com.smartnotify.resident.api.json.DeleteResidentRequest;
import com.smartnotify.resident.api.json.RegisterHorizontalCondoResidentRequest;
import com.smartnotify.resident.api.json.RegisterVerticalCondoResidentRequest;
import com.smartnotify.resident.service.HorizontalCondoResidentService;
import com.smartnotify.resident.service.VerticalCondoResidentService;
import com.smartnotify.resident.usecases.delete.factory.DeleteResidentFactory;
import com.smartnotify.resident.usecases.register.RegisterHorizontalCondoResident;
import com.smartnotify.resident.usecases.register.RegisterVerticalCondoResident;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/resident")
public class ResidentController {

    private final VerticalCondoResidentService verticalCondoResidentService;
    private final HorizontalCondoResidentService horizontalCondoResidentService;
    private final RegisterVerticalCondoResident registerVerticalCondoResident;
    private final RegisterHorizontalCondoResident registerHorizontalCondoResident;
    private final DeleteResidentFactory deleteResidentFactory;

    @PostMapping("/vertical-condo")
    public ResponseEntity<?> registerVerticalCondoResident(
            @RequestBody @Valid final RegisterVerticalCondoResidentRequest request) {

        final var resident = verticalCondoResidentService.buildVerticalCondoResident(request);
        registerVerticalCondoResident.execute(resident, Condominium.getAuthenticatedCondominium().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/horizontal-condo")
    public ResponseEntity<?> registerHorizontalCondoResident(
            @RequestBody @Valid final RegisterHorizontalCondoResidentRequest request) {

        final var resident = horizontalCondoResidentService.buildHorizontalCondoResident(request);
        registerHorizontalCondoResident.execute(resident, Condominium.getAuthenticatedCondominium().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public void deleteResident(@RequestBody @Valid final DeleteResidentRequest request) {
        final var condominium = Condominium.getAuthenticatedCondominium();
        final var strategy = deleteResidentFactory.findStrategy(condominium.getType());

        strategy.execute(request.getEmail(), condominium.getId());
    }

}
