package group.aist.cinemaapp.service.impl;


import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.model.Hall;
import group.aist.cinemaapp.repository.HallRepository;
import group.aist.cinemaapp.service.HallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepository halRepository;

    @Override
    public HallResponse getHallById(Long id) {
        var entity = fetchAuthorIfExist(id);
        return buildResponse(entity);
    }

    @Override
    public List<HallResponse> getHall() {
        return null;
    }

    @Override
    public void saveHall(HallRequest request) {

    }

    @Override
    public void updateHall(Long id, HallRequest request) {

    }

    @Override
    public void updateHallWithStatus(Long id, HallStatus status) {

    }

    @Override
    public void deleteHall(Long id) {

    }

    @Override
    public Hall fetchAuthorIfExist(Long id) {
        return null;
    }


//    @Override
//    public AuthorResponse getAuthorById(Long id) {
//        var entity = fetchAuthorIfExist(id);
//        return buildResponse(entity);
//    }
//
//    @Override
//    public void saveAuthor(AuthorSaveRequest request) {
//
//        var entity = buildEntity(request);
//        authorRepository.save(entity);
//    }
//
//    @Override
//    public void updateAuthor(Long id, AuthorUpdateRequest request) {
//
//        var entity = fetchAuthorIfExist(id);
//        updateEntity(entity, request);
//        authorRepository.save(entity);
//    }
//
//    @Override
//    public void updateAuthorWithStatus(Long id, AuthorStatus status) {
//        var entity = fetchAuthorIfExist(id);
//        entity.setStatus(status);
//        authorRepository.save(entity);
//
//    }
//
//    @Override
//    public void deleteAuthor(Long id) {
//
//        var entity = fetchAuthorIfExist(id);
//        entity.setStatus(DELETED);
//        authorRepository.save(entity);
//    }
//
//    @Override
//    public PageableResponse<AuthorResponse> getAuthors(PageCriteria pageCriteria) {
//        var resultsPage = authorRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(ASC,"fullName")),VISIBLE);
//        return buildPageableResponse(resultsPage);
//    }
//
//    @Override
//    public AuthorEntity fetchAuthorIfExist(Long id) {
//        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
//                "Author with id [%d] was not found!", id
//        )));
//    }
//}
}