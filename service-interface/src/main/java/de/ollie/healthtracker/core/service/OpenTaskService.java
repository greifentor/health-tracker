package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.OpenTask;
import java.util.List;

public interface OpenTaskService {
	List<OpenTask> findAllOpenTasks();
}
