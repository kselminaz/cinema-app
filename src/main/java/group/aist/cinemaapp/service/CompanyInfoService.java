package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.CompanyInfoCreateRequest;
import group.aist.cinemaapp.dto.request.CompanyInfoUpdateRequest;
import group.aist.cinemaapp.dto.request.HallCreateRequest;
import group.aist.cinemaapp.dto.request.HallUpdateRequest;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.CompanyInfo;
import group.aist.cinemaapp.model.Hall;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CompanyInfoService {

    CompanyInfoResponse getCompanyById(Long id);

    void saveCompanyInfo(CompanyInfoCreateRequest request,MultipartFile logoFile) ;

    void updateCompany(Long id, CompanyInfoUpdateRequest request);

    void deleteCompany(Long id);

    PageableResponse<CompanyInfoResponse> getCompanies(PageCriteria pageCriteria);

    CompanyInfo fetchCompanyIfExist(Long id);


}
