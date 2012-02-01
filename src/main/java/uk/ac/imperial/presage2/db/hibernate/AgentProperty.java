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

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class AgentProperty {

	@Id
	private String id;
	private UUID agentId;
	private String key;
	private String value;

	protected AgentProperty() {
		// for hibernate
	}

	AgentProperty(UUID agentId, String key, String value) {
		super();
		this.agentId = agentId;
		this.key = key;
		this.value = value;
		this.id = this.agentId + "-" + this.key;
	}

	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	UUID getAgentId() {
		return agentId;
	}

	void setAgentId(UUID agentId) {
		this.agentId = agentId;
	}

	String getKey() {
		return key;
	}

	void setKey(String key) {
		this.key = key;
	}

	String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

}
