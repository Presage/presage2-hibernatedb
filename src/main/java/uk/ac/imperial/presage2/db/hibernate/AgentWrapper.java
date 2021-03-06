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
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import uk.ac.imperial.presage2.core.db.persistent.PersistentAgent;
import uk.ac.imperial.presage2.core.db.persistent.TransientAgentState;

class AgentWrapper extends Updateable implements PersistentAgent {

	private Agent delegate;

	AgentWrapper(Agent delegate, SessionFactory sf) {
		super(sf);
		this.delegate = delegate;
	}

	@Override
	public UUID getID() {
		return UUID.fromString(delegate.getAid());
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public void setRegisteredAt(int time) {
		startTransaction();
		delegate.setRegisteredAt(time);
		update(delegate);
	}

	@Override
	public void setDeRegisteredAt(int time) {
		startTransaction();
		delegate.setDeRegisteredAt(time);
		update(delegate);
	}

	@Override
	public String getProperty(String key) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		AgentProperty property = (AgentProperty) s.get(AgentProperty.class,
				delegate.getAid() + "-" + key);
		s.getTransaction().commit();
		s.close();
		return property != null ? property.getValue() : null;
	}

	@Override
	public void setProperty(String key, String value) {
		startTransaction();
		AgentProperty property = new AgentProperty(delegate.getAid(), key,
				value.toString());
		save(property);
	}

	@Override
	public TransientAgentState getState(int time) {
		return new AgentStateWrapper(sessionFactory, this, time);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getProperties() {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		List<AgentProperty> propertyList = s
				.createQuery("from AgentProperty where agentId = ?")
				.setParameter(0, getID().toString()).list();
		Map<String, String> properties = new HashMap<String, String>();
		for (AgentProperty p : propertyList) {
			properties.put(p.getName(), p.getValue());
		}
		s.close();
		return properties;
	}

}
