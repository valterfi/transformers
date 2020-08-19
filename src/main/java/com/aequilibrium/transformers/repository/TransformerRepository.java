package com.aequilibrium.transformers.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.transformers.enums.TransformerType;
import com.aequilibrium.transformers.model.Transformer;

@Repository
public interface TransformerRepository extends PagingAndSortingRepository<Transformer, Long> {
	
	List<Transformer> findByTransformerTypeAndIdIn(TransformerType transformerType, List<Long> ids);

}
