/**
 * 
 */
package com.shi.products.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shi.products.vo.ProductsFamilyCombinationsVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface ProductsFamilyCombinationsRepo extends JpaRepository<ProductsFamilyCombinationsVO, String>{

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_FAMILY_COMBINATIONS WHERE productCode = ?1 AND isActive = 'TRUE' and flag = '0'")
	List<ProductsFamilyCombinationsVO> getProductsFamilyCombinations(String productCode);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE SHI_PRODUCTS_FAMILY_COMBINATIONS SET flag = '4' where productCode = :productCode AND isActive = 'True' and flag = '0'")
	void updateAllFamilyCombinationsInprogress(String productCode);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE SHI_PRODUCTS_FAMILY_COMBINATIONS SET flag = '1' where id = :id AND productCode = :productCode AND batchId = :batchId AND isActive = 'True'")
	void updateFamilyCombinationsInprogress(Long id, String productCode, String batchId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE SHI_PRODUCTS_FAMILY_COMBINATIONS SET flag = '2' where id = :id AND productCode = :productCode AND batchId = :batchId AND isActive = 'True'")
	void updateFamilyCombinationsCompleted(Long id, String productCode, String batchId);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE SHI_PRODUCTS_FAMILY_COMBINATIONS SET flag = '3' where productCode = :productCode AND isActive = 'True' and flag = '4'")
	void updateAllFamilyCombinationsFailed(String productCode);

}
