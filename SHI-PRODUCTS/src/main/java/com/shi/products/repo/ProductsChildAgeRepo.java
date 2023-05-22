/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductsChildAgeVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductsChildAgeRepo extends JpaRepository<ProductsChildAgeVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_CHILD_AGE WHERE productCode = ?1 AND period =?2 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsChildAgeVO> getProductsChildAge(String productCode, String period);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_CHILD_AGE WHERE productCode = ?1 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsChildAgeVO> getAgeList(String productCode);

}
