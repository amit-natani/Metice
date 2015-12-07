package com.metacube.metice.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.metacube.metice.Entity.Role;
import com.metacube.metice.dao.impl.RoleDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext-servlet.xml"})
public class RoleDaoImplTest {

	@Autowired
	private RoleDaoImpl roleDaoImpl;
	
	Role role = new Role();
	Role role1 = new Role();
	
	@Before
	public void setUp() throws Exception {
		role.setName("TSE");
		role1.setName("manager");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testCreateRole() {
		roleDaoImpl.createRole(role);
		assertEquals(role, roleDaoImpl.getRoleByName("TSE"));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRoleById() {
		roleDaoImpl.createRole(role);
		assertEquals(role, roleDaoImpl.getRoleById(role.getRoleId()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRoleByName() {
		roleDaoImpl.createRole(role);
		assertEquals(role, roleDaoImpl.getRoleByName("TSE"));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetRoles() {
		roleDaoImpl.createRole(role);
		roleDaoImpl.createRole(role1);
		assertEquals(2, roleDaoImpl.getRoles().size());
	}
	
	@After
	public void tearDown() throws Exception {
		role = null;
	}


}
