package com.datatable.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datatable.datatableconfig.DataTableConfig;
import com.datatable.repo.UserMasterRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	UserMasterRepository userRepo;
	
	@Autowired
	EntityManager entityManager;
	
	public Map<String, Object> getPaginatedData(HttpServletRequest request) {
		Map<String, Object> pageMap = new HashMap<>();
		DataTableConfig dataTableConfig = new DataTableConfig(request);
		/*DataTableConfig dataTableConfig = new DataTableConfig(request);
		Pageable pageable = null;
		if(dataTableConfig.getSearch() != null && !"".equals(dataTableConfig.getSearch()) && dataTableConfig.getSearch().trim().length() > 0) {
			pageable = PageRequest.of(dataTableConfig.getDraw().intValue() - 1, dataTableConfig.getLength().intValue());
		}
		Page<UserMaster> page = userRepo.findAll(pageable);
		pageMap.put("content", page.getContent());
		pageMap.put("page", dataTableConfig.getDraw().intValue());
		pageMap.put("pages", page.getTotalPages());
		pageMap.put("recordsTotal", page.getTotalPages());
		pageMap.put("recordsFiltered", page.get);
		pageMap.put("pageData", userRepo.findByFirstnameContainsOrLastnameContainsAllIgnoreCase(dataTableConfig.getSearch(), dataTableConfig.getSearch(), pageable));*/
		
		String mainQuery = "SELECT USERID, FIRSTNAME, LASTNAME FROM USERMASTER";
		
		String whereClause = "WHERE USERID = ?1 OR FIRSTNAME LIKE CONCAT('%', ?1, '%') OR LASTNAME LIKE CONCAT('%', ?1, '%')";
		
		String countQuery = "SELECT COUNT(*) FROM USERMASTER";
		
		String orderBy = "ORDER BY " + dataTableConfig.getColumnName() + " " + dataTableConfig.getSortDir();
		
		
		//SessionFactory sessionFactory = (SessionFactory) entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
		
		Session session = entityManager.unwrap(Session.class);
		
		Long recordsTotal = ((BigInteger)session.createNativeQuery(countQuery).getSingleResult()).longValue();
		Long recordsFiltered;
		List<?> userList = null;
		
		//Query query = null;
		
		if (dataTableConfig.getSearch().trim().length() > 0 && dataTableConfig.getSearch() != null && !"".equals(dataTableConfig.getSearch())) {
			mainQuery += " " + whereClause + " " + orderBy;
			countQuery += " " + whereClause;
		
			userList = session.createNativeQuery(mainQuery).setParameter(1, dataTableConfig.getSearch()).setFirstResult(dataTableConfig.getStart().intValue()).setMaxResults(dataTableConfig.getLength().intValue()).getResultList();
			recordsFiltered = ((BigInteger)session.createNativeQuery(countQuery).setParameter(1, dataTableConfig.getSearch()).getSingleResult()).longValue();
		} else {
			userList = session.createNativeQuery(mainQuery + " " + orderBy).setFirstResult(dataTableConfig.getStart().intValue()).setMaxResults(dataTableConfig.getLength().intValue()).getResultList();
			recordsFiltered = ((BigInteger)session.createNativeQuery(countQuery).getSingleResult()).longValue();
		}
		
		
		
		
		pageMap.put("recordsTotal", recordsTotal);
		pageMap.put("recordsFiltered", recordsFiltered);
		pageMap.put("userList", userList);
		return pageMap;
	}
}
