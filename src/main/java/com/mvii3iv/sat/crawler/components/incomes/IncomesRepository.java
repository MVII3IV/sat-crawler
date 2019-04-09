package com.mvii3iv.sat.crawler.components.incomes;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IncomesRepository extends MongoRepository<Incomes, String> {
    public Incomes findByFiscalId(String fiscalId);
    public List<Incomes> findByEmisorRFC(String emisorRFC);
}
