/**
 * 
 */
package com.shi.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shi.products.vo.ProductColumnVO;

/**
 * @author suman.raju
 *
 */
public interface ProductColumnRepo extends JpaRepository<ProductColumnVO, String>{

	@Query(nativeQuery = true, value = "SELECT * from SHI_PRODUCTS_COLUMN")
	ProductColumnVO getProductColumn();

}
