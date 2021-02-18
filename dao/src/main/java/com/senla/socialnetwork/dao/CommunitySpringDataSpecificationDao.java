package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CommunitySpringDataSpecificationDao extends JpaRepository<Community, Long>, JpaSpecificationExecutor<Community>, CommunityCriteriaApiDaoCustom {
    String GRAPH_NAME = "graph.Community";

    @Override
    @EntityGraph(value = GRAPH_NAME)
    Page<Community> findAll(Specification<Community> communitySpecification, Pageable pageable);

    @Override
    @EntityGraph(value = GRAPH_NAME)
    Page<Community> findAll(Pageable pageable);

    @Override
    @EntityGraph(value = GRAPH_NAME)
    Optional<Community> findById(Long id);

    @Override
    @EntityGraph(value = GRAPH_NAME)
    Optional<Community> findOne(Specification<Community> communitySpecification);

}
