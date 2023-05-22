/**
 * 
 */
package com.shi.products.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.QuotesAuditVO;

/**
 * @author suman.raju
 *
 */
@Repository
public interface QuotesAuditRepo extends JpaRepository<QuotesAuditVO, String>{

}
