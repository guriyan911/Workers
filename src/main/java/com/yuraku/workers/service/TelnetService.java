package com.yuraku.workers.service;

import java.io.IOException;
import java.net.SocketException;


public interface TelnetService {
	public void connect() throws SocketException, IOException;
	public String write(String message) throws IOException;
	public void close() throws IOException;
}
