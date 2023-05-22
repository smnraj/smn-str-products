/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductsParentAndParentInLawAgeVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductsParentAndParentInLawAgeRepo extends JpaRepository<ProductsParentAndParentInLawAgeVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_PARENT_PARENTINLAW_AGE WHERE productCode = ?1 AND period =?2 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsParentAndParentInLawAgeVO> getProductsParentAndParentInLawAge(String productCode, String period);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_PARENT_PARENTINLAW_AGE WHERE productCode = ?1 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsParentAndParentInLawAgeVO> getAgeList(String productCode);

}
