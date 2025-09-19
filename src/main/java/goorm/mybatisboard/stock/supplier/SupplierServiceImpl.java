package goorm.mybatisboard.stock.supplier;

import goorm.mybatisboard.stock.product.dto.SupplierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 공급업체 서비스 구현
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> findAllActive() {
        log.debug("활성 공급업체 목록 조회");
        return supplierMapper.findAllActive();
    }

    @Override
    public List<SupplierDto> findAllActiveOrSelected(Long selectedSupplierSeq) {
        log.debug("수정용 공급업체 목록 조회 (활성 + 선택된 공급업체: {})", selectedSupplierSeq);
        return supplierMapper.findAllActiveOrSelected(selectedSupplierSeq);
    }

    @Override
    public SupplierDto findBySeq(Long supplierSeq) {
        log.debug("공급업체 상세 조회: {}", supplierSeq);
        return supplierMapper.findBySeq(supplierSeq);
    }
}