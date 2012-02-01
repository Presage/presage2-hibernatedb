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

class Updateable {

	final SessionFactory sessionFactory;
	private Session session = null;

	Updateable(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	void startTransaction() {
		if (session == null)
			session = sessionFactory.openSession();
		session.beginTransaction();
	}

	void update(Object o) {
		session.update(o);
		session.getTransaction().commit();
		session.close();
		session = null;
	}

	void save(Object o) {
		session.save(o);
		session.getTransaction().commit();
		session.close();
		session = null;
	}
}
