package com.mvii3iv.sat.crawler.components.bills;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface to access to the data
 */
public interface BillsRepository extends MongoRepository<Bills, String> {
    public Bills findByFiscalId(String fiscalId);
    public List<Bills> findByEmisorRFC(String emisorRFC);
    public List<Bills> findByUserId(String userId);
}
