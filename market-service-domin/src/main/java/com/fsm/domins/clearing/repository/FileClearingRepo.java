package com.fsm.domins.clearing.repository;

import com.fsm.domins.clearing.models.FileClearing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "fileClearingRepo")
public interface FileClearingRepo extends MongoRepository<FileClearing, String> {

    FileClearing findByFileUuid(String fileUuid);

    void deleteByFileUuid(String fileUuid);
}
