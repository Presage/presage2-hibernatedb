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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import uk.ac.imperial.presage2.core.db.persistent.PersistentAgent;
import uk.ac.imperial.presage2.core.db.persistent.TransientAgentState;

class AgentStateWrapper extends Updateable implements TransientAgentState {

	final private AgentWrapper agent;
	final private int time;

	AgentStateWrapper(SessionFactory sessionFactory, AgentWrapper agent,
			int time) {
		super(sessionFactory);
		this.agent = agent;
		this.time = time;
	}

	@Override
	public int getTime() {
		return this.time;
	}

	@Override
	public PersistentAgent getAgent() {
		return agent;
	}

	@Override
	public String getProperty(String key) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		AgentState property = (AgentState) s.get(AgentState.class,
				agent.getID() + "-" + key + "-" + getTime());
		s.getTransaction().commit();
		s.close();
		return property != null ? property.getValue() : null;
	}

	@Override
	public void setProperty(String key, String value) {
		startTransaction();
		AgentState state = new AgentState(agent.getID(), key, getTime(),
				value.toString());
		save(state);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getProperties() {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		List<AgentState> propertyList = s
				.createQuery(
						"from AgentState where agentId = ? and timestep = ?")
				.setParameter(0, agent.getID()).setParameter(1, time).list();
		Map<String, String> properties = new HashMap<String, String>();
		for (AgentState p : propertyList) {
			properties.put(p.getName(), p.getValue());
		}
		return properties;
	}

}
