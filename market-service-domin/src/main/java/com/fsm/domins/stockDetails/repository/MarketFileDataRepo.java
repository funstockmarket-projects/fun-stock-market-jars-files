package com.fsm.domins.stockDetails.repository;

import com.fsm.domins.stockDetails.models.FileMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value="MarketFileDataRepo")
public interface MarketFileDataRepo extends MongoRepository<FileMetadata, String> {
     Optional<FileMetadata> findByFileName(String fileName);
     Optional<FileMetadata> findByFileUUID(String id);
}
