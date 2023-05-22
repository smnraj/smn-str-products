/**
 * 
 */
package com.shi.products.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductListVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductsRepo extends JpaRepository<ProductListVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_LIST WHERE PRODUCTCODE = ?1 AND ISACTIVE = 'TRUE'")
	List<ProductListVO> getProductInfo(String prodtCode);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_LIST WHERE PRODUCTCODE = :productCode AND planVariants = :planVariants AND ISACTIVE = 'TRUE'")
	List<ProductListVO> getProductInfoByPlan(String productCode, String planVariants);

}
