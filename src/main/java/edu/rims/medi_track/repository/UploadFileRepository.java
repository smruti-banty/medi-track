package edu.rims.medi_track.repository;

import edu.rims.medi_track.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, String> {
}
