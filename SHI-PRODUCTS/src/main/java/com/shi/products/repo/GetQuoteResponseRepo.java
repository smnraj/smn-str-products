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
import com.shi.products.vo.GetQuoteResponseVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface GetQuoteResponseRepo extends JpaRepository<GetQuoteResponseVO, String>{

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM SHI_PRODUCTS_GET_QUOTE_RESPONSE where productCode = :productCode AND getQuoteReqid = :getQuoteReqid AND isActive = 'TRUE'")
	void deleteInCompleteRequest(String productCode, Long getQuoteReqid);

	@Query(nativeQuery = true, value = "SELECT * FROM SHI_PRODUCTS_GET_QUOTE_RESPONSE WHERE productCode = :productCode AND onlineDiscountPerc = '5' and isActive = 'TRUE'")
	List<GetQuoteResponseVO> getUpFrontDiscountReq(String productCode);

}
