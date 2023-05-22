/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shi.products.vo.ProductsAdultAgeVO;

/**
 * @author suman.raju
 *
 */
public interface ProductsAdultAgeRepo extends JpaRepository<ProductsAdultAgeVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_ADULTS_AGE WHERE productCode = ?1 AND period = ?2 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsAdultAgeVO> getProductsAdultAge(String productCode, String period);
	
	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_ADULTS_AGE WHERE productCode = ?1 AND period = ?2 AND schemeCode = '1A' AND isActive = 'TRUE' and flag = '0'")
	List<ProductsAdultAgeVO> getAssureAdultAge(String productCode, String period);
	
	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_ADULTS_AGE WHERE productCode = ?1 AND period = ?2 AND schemeCode = 'ALL' AND isActive = 'TRUE' and flag = '0'")
	List<ProductsAdultAgeVO> getAssureAdultsAge(String productCode, String period);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_ADULTS_AGE WHERE productCode = ?1 AND period = ?2 AND plan = ?3 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsAdultAgeVO> getMediClassicAdultAge(String productCode, String period, String plan);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_ADULTS_AGE WHERE productCode = ?1 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsAdultAgeVO> getAgeList(String productCode);

}
