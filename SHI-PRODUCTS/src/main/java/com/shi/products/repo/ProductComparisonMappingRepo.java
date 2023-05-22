/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductComparisonMappingVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductComparisonMappingRepo  extends JpaRepository<ProductComparisonMappingVO, String>{
	
	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_COMPARISON_MAPPING WHERE PRODUCTCODE = ?1 AND combination = ?2 AND isActive = 'True'")
	public List<ProductComparisonMappingVO> getProductComparisonMapping(String productCode, String combination);

}
