package group.aist.cinemaapp.controller;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.CompanyInfoCreateRequest;
import group.aist.cinemaapp.dto.request.CompanyInfoUpdateRequest;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.CompanyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/company-info")
public class CompanyInfoController {
    private final CompanyInfoService companyService;
    @GetMapping("/{id}")
    public CompanyInfoResponse getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }
    @GetMapping
    public PageableResponse<CompanyInfoResponse> getCompanies(PageCriteria pageCriteria) {
        return companyService.getCompanies(pageCriteria);
    }
    @PostMapping(consumes = "multipart/form-data")
    public void saveCompanyInfo(@RequestPart("data") CompanyInfoCreateRequest companyCreateRequest, @RequestPart("logo") MultipartFile logo) {
        companyService.saveCompanyInfo(companyCreateRequest,logo);
    }
    @PutMapping("/{id}")
    public void updateCompany(@PathVariable Long id, @RequestBody CompanyInfoUpdateRequest request) {
        companyService.updateCompany(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
