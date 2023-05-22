/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductsSumInsureVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductsSumInsureRepo extends JpaRepository<ProductsSumInsureVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_YEARS_SUMINSURE WHERE productCode = ?1 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsSumInsureVO> getProductsSumInsure(String productCode);
	
//	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_YEARS_SUMINSURE WHERE productCode = :productCode AND sumInsure >= :sumInsure AND period = :period AND isActive = 'TRUE' and flag = '0' ORDER BY LENGTH(sumInsure), suminsure ASC LIMIT 1 ")
	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_YEARS_SUMINSURE WHERE productCode = :productCode AND sumInsure >= :sumInsure AND period = :period AND isActive = 'TRUE' ORDER BY LENGTH(sumInsure), suminsure ASC LIMIT 1 ")
	ProductsSumInsureVO getAvailableSumInsure(String productCode, String sumInsure, String period);

}
