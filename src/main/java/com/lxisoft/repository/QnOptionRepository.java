package com.lxisoft.repository;

import com.lxisoft.domain.QnOption;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QnOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QnOptionRepository extends JpaRepository<QnOption, Long> {
}
