/**
 * 
 */
package com.shi.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.ProductPincodeVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductPincodeRepo extends JpaRepository<ProductPincodeVO, String>{

	@Query(nativeQuery = true, value= "SELECT zone FROM SHI_PRODUCTS_PINCODE WHERE PRODUCTCODE = :productCode AND PINCODE = :pincode")
	String getPincodeByZone(String productCode, String pincode);

}
