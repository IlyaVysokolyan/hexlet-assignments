package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

import java.util.List;

// BEGIN
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    //Преобразование сущности в ДТО с учетом разных полей
    @Mapping(target = "title", source = "name")
    @Mapping(target = "price", source = "cost")
    @Mapping(target = "vendorCode", source = "barcode")
    public abstract ProductDTO toDto(Product model);

    //Преобразование списка сущностей в список объектов ДТО
    public abstract List<ProductDTO> toProductListDTO(List<Product> modelList);

    //GПреобразование из ДТО в сущность с учетом разных полей
    @Mapping(target = "name", source = "title")
    @Mapping(target = "cost", source = "price")
    @Mapping(target = "barcode", source = "vendorCode")
    public abstract Product createDTO(ProductCreateDTO dto);

    @Mapping(target = "cost", source = "price")
    public abstract void update(ProductUpdateDTO dto,@MappingTarget Product product);

}
// END
