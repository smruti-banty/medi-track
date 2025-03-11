package edu.rims.medi_track.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "upload_file")
@Setter
@Getter
public class UploadFile {
    @Id
    @Column(name = "upload_id")
    private String uploadId;

    @Column(name = "file_name")
    private String fileName;
}
