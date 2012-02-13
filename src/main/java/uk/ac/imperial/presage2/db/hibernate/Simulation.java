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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
class Simulation {

	private Long id;
	private String name;
	private String state;
	private String className;
	private Integer currentTime;
	private Integer finishTime;
	private Long createdAt;
	private Long startedAt;
	private Long finishedAt;
	private Long parent;

	Simulation() {
	}

	Simulation(String name, String classname, String state, int finishTime) {
		this.name = name;
		this.className = classname;
		this.state = state;
		this.finishTime = finishTime;
		this.createdAt = Long.valueOf(new Date().getTime());
		this.currentTime = 0;
		this.startedAt = 0L;
		this.finishedAt = 0L;
		this.parent = null;
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getState() {
		return state;
	}

	void setState(String state) {
		this.state = state;
	}

	String getClassName() {
		return className;
	}

	void setClassName(String className) {
		this.className = className;
	}

	Integer getCurrentTime() {
		return currentTime;
	}

	void setCurrentTime(Integer currentTime) {
		this.currentTime = currentTime;
	}

	Integer getFinishTime() {
		return finishTime;
	}

	void setFinishTime(Integer finishTime) {
		this.finishTime = finishTime;
	}

	Long getCreatedAt() {
		return createdAt;
	}

	void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	Long getStartedAt() {
		return startedAt;
	}

	void setStartedAt(Long startedAt) {
		this.startedAt = startedAt;
	}

	Long getFinishedAt() {
		return finishedAt;
	}

	void setFinishedAt(Long finishedAt) {
		this.finishedAt = finishedAt;
	}

	Long getParent() {
		return parent;
	}

	void setParent(Long parent) {
		this.parent = parent;
	}

}
