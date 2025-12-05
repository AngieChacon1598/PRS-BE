package pe.edu.vallegrande.configurationservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.configurationservice.model.Position;
import pe.edu.vallegrande.configurationservice.service.PositionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.UUID;


@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {


    private final PositionService service;


    // 🔹 Get all active positions
    @GetMapping
    public Flux<Position> getAllActive() {
        return service.getAllActive();
    }


    // 🔹 Get all inactive positions
    @GetMapping("/inactive")
    public Flux<Position> getAllInactive() {
        return service.getAllInactive();
    }


    // 🔹 Get a position by ID
    @GetMapping("/{id}")
    public Mono<Position> getById(@PathVariable UUID id) {
        return service.getById(id);
    }


    // 🔹 Create a new position
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Position> create(@RequestBody Position position) {
        return service.create(position);
    }


    // 🔹 Update an existing position
    @PutMapping("/{id}")
    public Mono<Position> update(@PathVariable UUID id, @RequestBody Position position) {
        return service.update(id, position);
    }


    // 🔹 Soft delete (logical deletion)
    @DeleteMapping("/{id}")
    public Mono<Position> softDelete(@PathVariable UUID id) {
        return service.softDelete(id);
    }


    // 🔹 Restore an inactive position
    @PatchMapping("/{id}/restore")
    public Mono<Position> restore(@PathVariable UUID id) {
        return service.restore(id);
    }
}
