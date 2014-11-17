package com.yuraku.workers.service;

import java.io.IOException;


public interface TelnetService {
	public boolean connect() throws IOException;
	public String write(String message) throws IOException;
	public void close() throws IOException;
}
