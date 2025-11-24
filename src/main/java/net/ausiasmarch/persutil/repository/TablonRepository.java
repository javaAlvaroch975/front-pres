package net.ausiasmarch.persutil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.persutil.entity.TablonEntity;

public interface TablonRepository extends JpaRepository<TablonEntity, Long> {
    
}
