package com.aequilibrium.transformers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.model.Transformer;

@Repository
public interface TransformerRepository extends CrudRepository<Transformer, Integer> {

}
