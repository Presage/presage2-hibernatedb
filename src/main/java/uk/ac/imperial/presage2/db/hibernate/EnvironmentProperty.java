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

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class EnvironmentProperty {

	@Id
	private String id;
	private Long simId;
	private String key;
	private Integer timestep = null;
	private String value;

	protected EnvironmentProperty() {
		// for hibernate
	}

	EnvironmentProperty(Long simId, String key, String value) {
		super();
		this.simId = simId;
		this.key = key;
		this.value = value;
		this.timestep = null;
		this.id = this.simId + "-" + this.key;
	}

	EnvironmentProperty(Long simId, String key, Integer timestep, String value) {
		super();
		this.simId = simId;
		this.key = key;
		this.timestep = timestep;
		this.value = value;
		this.id = this.simId + "-" + this.key + "-" + this.timestep;
	}

	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	Long getSimId() {
		return simId;
	}

	void setSimId(Long simId) {
		this.simId = simId;
	}

	String getKey() {
		return key;
	}

	void setKey(String key) {
		this.key = key;
	}

	Integer getTimestep() {
		return timestep;
	}

	void setTimestep(Integer timestep) {
		this.timestep = timestep;
	}

	String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

}
