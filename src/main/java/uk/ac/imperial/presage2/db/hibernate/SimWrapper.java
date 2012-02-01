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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import uk.ac.imperial.presage2.core.db.persistent.PersistentAgent;
import uk.ac.imperial.presage2.core.db.persistent.PersistentEnvironment;
import uk.ac.imperial.presage2.core.db.persistent.PersistentSimulation;

class SimWrapper extends Updateable implements PersistentSimulation {

	Simulation delegate;
	EnvironmentWrapper environment;

	SimWrapper(Simulation delegate, SessionFactory sf) {
		super(sf);
		this.delegate = delegate;
		this.environment = new EnvironmentWrapper(this, sf);
	}

	@Override
	public long getID() {
		return delegate.getId();
	}

	@Override
	public void addParameter(String name, Object value) {
		startTransaction();
		Parameter param = new Parameter(getID(), name, value.toString());
		save(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getParameters() {
		Map<String, Object> params = new HashMap<String, Object>();
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		List<Parameter> parameters = s.createQuery(
				"from Parameter WHERE simId = " + getID()).list();
		for (Parameter p : parameters) {
			params.put(p.getKey(), p.getValue());
		}
		s.getTransaction().commit();
		s.close();
		return Collections.unmodifiableMap(params);
	}

	@Override
	public int getFinishTime() {
		return delegate.getFinishTime();
	}

	@Override
	public void setCurrentTime(int time) {
		startTransaction();
		delegate.setCurrentTime(time);
		update(delegate);
	}

	@Override
	public int getCurrentTime() {
		return delegate.getCurrentTime();
	}

	@Override
	public void setState(String newState) {
		startTransaction();
		delegate.setState(newState);
		update(delegate);
	}

	@Override
	public String getState() {
		return delegate.getState();
	}

	@Override
	public void setParentSimulation(PersistentSimulation parent) {
		startTransaction();
		delegate.setParent(parent.getID());
		update(delegate);
	}

	@Override
	public PersistentSimulation getParentSimulation() {
		Long parent = delegate.getParent();
		if (parent == null)
			return null;
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		PersistentSimulation sim = new SimWrapper((Simulation) s.get(
				Simulation.class, parent), sessionFactory);
		s.getTransaction().commit();
		s.close();
		return sim;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getChildren() {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		List<Long> children = s.createQuery(
				"select id from Simulation where parent = " + getID()).list();
		s.getTransaction().commit();
		s.close();
		return children;
	}

	@Override
	public void setFinishedAt(long time) {
		startTransaction();
		delegate.setFinishedAt(time);
		update(delegate);
	}

	@Override
	public long getFinishedAt() {
		return delegate.getFinishedAt();
	}

	@Override
	public void setStartedAt(long time) {
		startTransaction();
		delegate.setStartedAt(time);
		update(delegate);
	}

	@Override
	public long getStartedAt() {
		return delegate.getStartedAt();
	}

	@Override
	public long getCreatedAt() {
		return delegate.getCreatedAt();
	}

	@Override
	public String getClassName() {
		return delegate.getClassName();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public Set<PersistentAgent> getAgents() {
		// TODO
		Set<PersistentAgent> agents = new HashSet<PersistentAgent>();
		for (Agent a : delegate.getAgents()) {
			agents.add(new AgentWrapper(a, this.sessionFactory));
		}
		return agents;
	}

	@Override
	public PersistentEnvironment getEnvironment() {
		return this.environment;
	}

}
