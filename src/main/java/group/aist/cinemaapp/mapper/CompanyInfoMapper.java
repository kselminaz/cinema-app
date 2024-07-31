package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.CompanyInfoCreateRequest;
import group.aist.cinemaapp.dto.response.CompanyInfoResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.CompanyInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CompanyInfoMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "logo",ignore = true)
    CompanyInfo toEntity(CompanyInfoCreateRequest request);
    @Mapping(target = "logo",source = "logo",qualifiedByName = "normalizePath")
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
    @Named("normalizePath")
    default String normalizePath(String path) {
        return path.replace("\\","/");}

}