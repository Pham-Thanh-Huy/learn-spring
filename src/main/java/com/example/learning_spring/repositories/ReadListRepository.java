package com.example.learning_spring.repositories;

import com.example.learning_spring.models.ReadList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReadListRepository extends JpaRepository<ReadList, Long> {
    @Query("select rl from ReadList rl where rl.user.id = :userId")
    Page<ReadList> findByUserId(Pageable pageable, @Param("userId") Long id);
}
