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
class Agent {

	@Id
	private String id;
	private Long simId;
	private String aid;
	private String name;
	private Integer registeredAt;
	private Integer deRegisteredAt;

	Agent() {
		super();
	}

	Agent(Long simId, String id, String name) {
		super();
		this.simId = simId;
		this.aid = id;
		this.name = name;
		this.id = this.simId + "-" + this.aid;
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

	String getAid() {
		return aid;
	}

	String getName() {
		return name;
	}

	Integer getRegisteredAt() {
		return registeredAt;
	}

	Integer getDeRegisteredAt() {
		return deRegisteredAt;
	}

	void setAid(String id) {
		this.aid = id;
	}

	void setName(String name) {
		this.name = name;
	}

	void setRegisteredAt(Integer registeredAt) {
		this.registeredAt = registeredAt;
	}

	void setDeRegisteredAt(Integer deRegisteredAt) {
		this.deRegisteredAt = deRegisteredAt;
	}

}
