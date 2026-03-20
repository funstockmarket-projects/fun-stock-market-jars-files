package com.fsm.domins.information.repository;

import com.fsm.domins.information.models.FileInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "FileInformationRepo")
public interface FileInformationRepo extends MongoRepository<FileInformation, String> {

    Optional<FileInformation> findByFileName(String fileName);

}

