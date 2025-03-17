package edu.rims.medi_track.repository;

import edu.rims.medi_track.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByUserIdOrderByCreatedDateDesc(String userId);

    List<Activity> findTop5ByUserIdOrderByCreatedDateDesc(String userId);
}

