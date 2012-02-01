/**
 * 	Copyright (C) 2011 Sam Macbeth <sm1106 [at] imperial [dot] ac [dot] uk>
 *
 * 	This file is part of Presage2.
 *
 *     Presage2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Presage2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser Public License
 *     along with Presage2.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.imperial.presage2.db.hibernate;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import uk.ac.imperial.presage2.core.db.DatabaseService;
import uk.ac.imperial.presage2.core.db.StorageService;
import uk.ac.imperial.presage2.core.db.Transaction;
import uk.ac.imperial.presage2.core.db.persistent.PersistentAgent;
import uk.ac.imperial.presage2.core.db.persistent.PersistentSimulation;
import uk.ac.imperial.presage2.core.db.persistent.SimulationFactory;
import uk.ac.imperial.presage2.core.db.persistent.TransientAgentState;

public class HibernateService implements DatabaseService, StorageService {

	private SessionFactory sessionFactory = null;
	SimWrapper currentSim = null;

	HibernateService() {
		super();
	}

	@Override
	public void start() throws Exception {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public boolean isStarted() {
		return sessionFactory != null;
	}

	@Override
	public void stop() {
		if (isStarted())
			sessionFactory.close();
	}

	@Override
	public PersistentSimulation createSimulation(String name, String classname,
			String state, int finishTime) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Simulation sim = new Simulation(name, classname, state, finishTime);
		session.save(sim);
		session.getTransaction().commit();
		currentSim = new SimWrapper(sim, sessionFactory);
		session.close();
		return currentSim;
	}

	@Override
	public PersistentSimulation getSimulation() {
		return currentSim;
	}

	@Override
	public PersistentSimulation getSimulationById(long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Simulation> result = session.createQuery(
				"from Simulation as sim where sim.id = " + id).list();
		try {
			return new SimWrapper(result.get(0), sessionFactory);
		} catch (IndexOutOfBoundsException e) {
			return null;
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}

	@Override
	public List<Long> getSimulations() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Long> results = session.createQuery("select id from Simulation")
				.list();
		session.getTransaction().commit();
		session.close();
		return results;
	}

	@Override
	public void setSimulation(PersistentSimulation sim) {
		this.currentSim = (SimWrapper) sim;
	}

	@Override
	public SimulationFactory getSimulationFactory() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PersistentAgent createAgent(UUID agentID, String name) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Agent a = new Agent(currentSim.getID(), agentID, name);
		session.save(a);
		session.getTransaction().commit();
		session.close();
		return new AgentWrapper(a, sessionFactory);
	}

	@Override
	public PersistentAgent getAgent(UUID agentID) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		Agent agent = (Agent) s.get(Agent.class, currentSim.getID() + "-"
				+ agentID);
		s.getTransaction().commit();
		s.close();
		return agent != null ? new AgentWrapper(agent, sessionFactory) : null;
	}

	@Override
	public TransientAgentState getAgentState(UUID agentID, int time) {
		PersistentAgent agent = getAgent(agentID);
		return agent != null ? agent.getState(time) : null;
	}

	@Override
	public Transaction startTransaction() {
		throw new UnsupportedOperationException("Transactions depreciated.");
	}

}
