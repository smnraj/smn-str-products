/**
 * 
 */
package com.shi.products.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shi.products.vo.AgeAndAgeBandVO;

/**
 * @author suman.raju
 *
 */

@Repository
public interface AgeAndAgeBandRepo extends JpaRepository<AgeAndAgeBandVO, String>{

	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBands(String productCode, String age);
	
	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND period = :period AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandsByPeriod(String productCode, String age, String period);

	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND person = :person AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandByPerson(String productCode, String age, String person);
	
	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND person = :person AND period = :period AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandByPersonAndPeriod(String productCode, String age, String person, String period);
	
	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND period = :period AND person = :person AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandsByPeriodAndPerson(String productCode, String age, String period, String person);

	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND period = :period AND person = :person AND schemeCode = :schemeCode AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandsByPeriodAndPersonAndScheme(String productCode, String age,
			String period, String person, String schemeCode);

	@Query(nativeQuery = true, value = "SELECT ageBandInYears FROM SHI_PRODUCTS_AGE_AND_AGEBANDS WHERE productCode = :productCode AND age = :age AND period = :period AND plan = :plan AND flag = '0' and isActive = 'TRUE'")
	ArrayList<String> getAgeBandsByPeriodAndPlan(String productCode, String age, String period, String plan);
}
