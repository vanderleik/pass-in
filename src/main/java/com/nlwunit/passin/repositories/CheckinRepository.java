package com.nlwunit.passin.repositories;

import com.nlwunit.passin.domain.checkin.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<Checkin, Integer> {

}
