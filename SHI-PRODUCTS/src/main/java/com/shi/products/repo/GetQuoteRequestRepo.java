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

import com.shi.products.vo.GetQuoteRequestVO;

/**
 * @author suman.raju
 *
 */

@Repository
public interface GetQuoteRequestRepo extends JpaRepository <GetQuoteRequestVO, String> {

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_GET_QUOTE_REQUEST WHERE productCode = :productCode AND flag = '0' and isActive = 'TRUE'")
	List<GetQuoteRequestVO> getBatchRequests(String productCode);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update SHI_PRODUCTS_GET_QUOTE_REQUEST set flag = '4', modifiedTime = current_timestamp where productCode = :productCode AND flag = '0' and isActive = 'TRUE'")
	void updateGetQuoteRequestInQueue(String productCode);

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update SHI_PRODUCTS_GET_QUOTE_REQUEST set flag = '1', modifiedTime = current_timestamp where id = :id and productCode = :productCode AND flag = '4' and isActive = 'TRUE'")
	void updateGetQuoteRequestInProgress(String productCode, Long id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update SHI_PRODUCTS_GET_QUOTE_REQUEST set flag = '2', modifiedTime = current_timestamp where id = :id and productCode = :productCode AND flag = '1' and isActive = 'TRUE'")
	void updateGetQuoteRequestCompleted(String productCode, Long id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update SHI_PRODUCTS_GET_QUOTE_REQUEST set flag = '3', modifiedTime = current_timestamp where productCode = :productCode AND id = :getQuoteReqid AND flag in ('1','4') AND isActive = 'TRUE'")
	void updateGetQuoteRequestFailed(String productCode, Long getQuoteReqid);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_GET_QUOTE_REQUEST WHERE productCode = :productCode AND flag = '3' and isActive = 'TRUE'")
	List<GetQuoteRequestVO> getBatchFailedRequests(String productCode);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update SHI_PRODUCTS_GET_QUOTE_REQUEST set flag = '4', modifiedTime = current_timestamp where productCode = :productCode AND flag = '3' and isActive = 'TRUE'")
	void updateScheduleGetQuoteRequestInQueue(String productCode);

}
