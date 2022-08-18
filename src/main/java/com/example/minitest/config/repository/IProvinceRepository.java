package com.example.minitest.config.repository;

import com.example.minitest.config.model.Country;
import com.example.minitest.config.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Long> {
    @Query(value = "select * from province where name like :name", nativeQuery = true)
    Page<Province> searchPage(@Param("name") String name, Pageable pageable);

    Page<Province> findAllByCountry_Id(Long id, Pageable pageable);

    List<Province> findTop3ByOrderByPopular();

    List<Province> findTop3ByOrderByAreaDesc();
}
