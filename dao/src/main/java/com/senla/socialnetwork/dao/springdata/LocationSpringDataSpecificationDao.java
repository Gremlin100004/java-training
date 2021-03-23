package com.senla.socialnetwork.dao.springdata;

import com.senla.socialnetwork.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationSpringDataSpecificationDao extends JpaRepository<Location, Long>, LocationCriteriaApiDaoCustom {

}
