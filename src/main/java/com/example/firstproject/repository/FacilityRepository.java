package com.example.firstproject.repository;


import com.example.firstproject.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacilityRepository extends JpaRepository<Facility, String> {

    @Query(value = "SELECT * FROM facility", nativeQuery = true)
    Page<Facility> findByFacility(Pageable pageable);

    @Query(value =
            "SELECT  * " +
                    "FROM facility " +
                    "WHERE facility_code like ?1 " +
                    "OR facility_name like ?1 ",
            nativeQuery = true)
    Page<Facility> findBySearch(String searchTerm, Pageable pageable);

    @Query(value = "SELECT * FROM facility WHERE facility_code = ?1", nativeQuery = true)
    Facility findByFacilityCode(String facilityCode);

}
