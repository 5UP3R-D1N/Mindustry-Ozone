/*
 * Copyright 2021 Itzbenz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Ozone.Commands.Task;

public abstract class UpdateBasedTimeTask extends Task {//need to be implemented before used
	Runnable run;
	long lastRun;
	long interval;
	
	public UpdateBasedTimeTask() {
	
	}
	
	public UpdateBasedTimeTask(Runnable r, long ms) {
		run = r;
		interval = ms;
		lastRun = System.currentTimeMillis() - ms - 1;
	}
	
	public void run() {
		if (run != null) run.run();
	}
	
	public void update() {
		if ((System.currentTimeMillis() - lastRun) > interval) run();
	}
	
}