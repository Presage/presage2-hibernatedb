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

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import uk.ac.imperial.presage2.core.db.persistent.PersistentEnvironment;
import uk.ac.imperial.presage2.core.db.persistent.PersistentSimulation;

class EnvironmentWrapper extends Updateable implements PersistentEnvironment {

	final PersistentSimulation sim;

	EnvironmentWrapper(PersistentSimulation sim, SessionFactory sessionFactory) {
		super(sessionFactory);
		this.sim = sim;
	}

	@Override
	public Object getProperty(String key) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		EnvironmentProperty property = (EnvironmentProperty) s.get(
				EnvironmentProperty.class, sim.getID() + "-" + key);
		s.getTransaction().commit();
		s.close();
		return property != null ? property.getValue() : null;
	}

	@Override
	public void setProperty(String key, Object value) {
		startTransaction();
		EnvironmentProperty property = new EnvironmentProperty(sim.getID(),
				key, value.toString());
		save(property);
	}

	@Override
	public Object getProperty(String key, int timestep) {
		Session s = sessionFactory.openSession();
		s.beginTransaction();
		EnvironmentProperty property = (EnvironmentProperty) s.get(
				EnvironmentProperty.class, sim.getID() + "-" + key + "-"
						+ timestep);
		s.getTransaction().commit();
		s.close();
		return property != null ? property.getValue() : null;
	}

	@Override
	public void setProperty(String key, int timestep, Object value) {
		startTransaction();
		EnvironmentProperty property = new EnvironmentProperty(sim.getID(),
				key, timestep, value.toString());
		save(property);
	}

}
