package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.CompanyInfoCreateRequest;
import group.aist.cinemaapp.dto.request.CompanyInfoUpdateRequest;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.mapper.CompanyInfoMapper;
import group.aist.cinemaapp.model.CompanyInfo;

import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.repository.CompanyInfoRepository;
import group.aist.cinemaapp.service.CompanyInfoService;
import group.aist.cinemaapp.util.ImageSaveUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private final CompanyInfoRepository companyRepository;
    private final CompanyInfoMapper companyMapper;
    private final ImageSaveUtil imageSaveUtil;

    @Override
    public CompanyInfoResponse getCompanyById(Long id) {
        var entity = fetchCompanyIfExist(id);
        return companyMapper.toResponse(entity);
    }

    @Override
    public void saveCompanyInfo(CompanyInfoCreateRequest request, MultipartFile logo) {
        CompanyInfo companyInfo = companyMapper.toEntity(request);
        try {
            companyInfo.setLogo(imageSaveUtil.saveImage(logo, "logo"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        companyRepository.save(companyInfo);
    }

    @Override
    public void updateCompany(Long id, CompanyInfoUpdateRequest request) {
        var entity = fetchCompanyIfExist(id);
        ofNullable(request.getName()).ifPresent(entity::setName);
        ofNullable(request.getAboutText()).ifPresent(entity::setAboutText);
        ofNullable(request.getLogo()).ifPresent(entity::setLogo);
        ofNullable(request.getCostumersInformationText()).ifPresent(entity::setCostumersInformationText);
        companyRepository.save(entity);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);


    }

    @Override
    public PageableResponse<CompanyInfoResponse> getCompanies(PageCriteria pageCriteria) {
        var resultsPage = companyRepository.findAll(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(Sort.Direction.ASC, "title")));
        return companyMapper.toPageableResponse(resultsPage);
    }

    @Override
    public CompanyInfo fetchCompanyIfExist(Long id) {
        return companyRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, String.format(
                "Company with id [%d] was not found!", id
        )));

    }
}
