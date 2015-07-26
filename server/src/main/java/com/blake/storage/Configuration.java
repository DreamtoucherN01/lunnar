package com.blake.storage;

public class Configuration {
	final public int MAXLENGTH = 10; 
	
	private String serverIp;
	private String serverName;
	private String backSercername;
	private String backServerIp;
	private int nodeNumber;
	
	private Node node[];
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getBackSercername() {
		return backSercername;
	}

	public void setBackSercername(String backSercername) {
		this.backSercername = backSercername;
	}

	public String getBackServerIp() {
		return backServerIp;
	}

	public void setBackServerIp(String backServerIp) {
		this.backServerIp = backServerIp;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public Node[] getNode() {
		return node;
	}

	public void setNode(Node node[]) {
		this.node = node;
	}

	public class Node{
		private String nodeIp;
		private String nodeName;
		public String getNodeIp() {
			return nodeIp;
		}
		public void setNodeIp(String nodeIp) {
			this.nodeIp = nodeIp;
		}
		public String getNodeName() {
			return nodeName;
		}
		public void setNodeName(String nodeName) {
			this.nodeName = nodeName;
		}
	}

}
