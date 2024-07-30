package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.CompanyInfoCreateRequest;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.CompanyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CompanyInfoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    CompanyInfo toEntity(CompanyInfoCreateRequest request);

    @Mapping(target = "logo", source = "logo", qualifiedByName = "processLogo")
    CompanyInfoResponse toResponse(CompanyInfo entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getCompanyInfoList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<CompanyInfoResponse> toPageableResponse(Page<CompanyInfo> page);

    @Named("getCompanyInfoList")
    default List<CompanyInfoResponse> getCompanyInfoList(List<CompanyInfo> companyInfos) {
        return companyInfos.stream().map(this::toResponse).toList();
    }

    @Named("processLogo")
    default String processLogo(String logoPath) {
        try {
            if (logoPath != null) {
                byte[] fileContent = Files.readAllBytes(Paths.get(logoPath));
                return Base64.getEncoder().encodeToString(fileContent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the logo image", e);
        }
        return null;
    }

}