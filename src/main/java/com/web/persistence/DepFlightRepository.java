package com.web.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.DepFlightDTO;

public interface DepFlightRepository extends JpaRepository<DepFlightDTO, String>{


}
