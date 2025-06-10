package dev.val.Hospital_Registration_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dev.val.Hospital_Registration_API.model.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {

}