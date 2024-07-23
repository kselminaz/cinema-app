package group.aist.cinemaapp.controller;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.HallCreateRequest;
import group.aist.cinemaapp.dto.request.HallUpdateRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.HallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
@RestController
@RequestMapping("v1/halls")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;
    @GetMapping("/{id}")
    public HallResponse getHallById(@PathVariable Long id) {
        return hallService.getHallById(id);
    }
    @GetMapping
    public PageableResponse<HallResponse> getHalls(PageCriteria pageCriteria) {
        return hallService.getHalls(pageCriteria);
    }
    @PostMapping
    @ResponseStatus(CREATED)
    public void saveHall(@Valid @RequestBody HallCreateRequest request) {
        hallService.saveHall(request);
    }
    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateHall(@PathVariable Long id, @Valid @RequestBody HallUpdateRequest request) {
        hallService.updateHall(id, request);
    }
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateHallWithStatus(@PathVariable Long id, @RequestParam String status) {
        hallService.updateHallWithStatus(id, status);
    }
}