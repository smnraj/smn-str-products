/**
 * 
 */
package com.shi.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.SourceSystemVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface SourceSystemRepo extends JpaRepository<SourceSystemVO, String>{
	
	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_SOURCE_SYSTEM where sourceSystem = :sourceSystem  AND productCode = :productCode AND divisonCode = :divisonCode AND isActive = 'TRUE'")
	SourceSystemVO getSourceSystem(String sourceSystem, String productCode, String divisonCode);

}
