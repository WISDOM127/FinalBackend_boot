package com.web.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.domain.DepFlightDTO;

public interface DepFlightRepository extends JpaRepository<DepFlightDTO, String>{

	
	public List<DepFlightDTO> findByRemarkContaining(String Keyword);
	
	public Page<DepFlightDTO> findAll(Pageable pageable);
	
	public Page<DepFlightDTO> findByFlightIdContaining(String searchKeyword, Pageable pageable);

	public Page<DepFlightDTO> findByRemarkContaining(String searchKeyword, Pageable pageable);

	public Page<DepFlightDTO> findByRemarkContainingAndFlightIdContaining(String remark, Pageable pageable, String searchKeyword);

	

//	@Query("SELECT d FROM DepFlightDTO d WHERE d.remark LIKE %:keyword% ORDER BY d.estimatedDateTime")
//    List<DepFlightDTO> findByRemarkContaining(@Param("keyword") String keyword);

}
