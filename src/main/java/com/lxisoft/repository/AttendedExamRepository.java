package com.lxisoft.repository;

import com.lxisoft.domain.AttendedExam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttendedExam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendedExamRepository extends JpaRepository<AttendedExam, Long> {
}
