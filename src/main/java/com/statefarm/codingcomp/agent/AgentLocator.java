package com.statefarm.codingcomp.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.statefarm.codingcomp.bean.Agent;
import com.statefarm.codingcomp.bean.Office;
import com.statefarm.codingcomp.bean.USState;
import com.statefarm.codingcomp.utilities.SFFileReader;

/**
 * Only US Agents are applicable since State Farm is currently in the process of
 * selling off its Canadian business.
 */
@Component
public class AgentLocator {
	@Autowired
	private AgentParser agentParser;

	@Autowired
	private SFFileReader sfFileReader;
	
	
	/**
	 * Find agents where the URL of their name contains the firstName and
	 * lastName For instance, Tom Newman would search for "Tom-" and "-Newman"	
	 * in the URL.
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public List<Agent> getAgentsByName(String firstName, String lastName) {
		List<String> myList = sfFileReader.findHtmlFiles();
		List<Agent> myAgents = new ArrayList<Agent>();
		for(String str: myList) {
		    if((str.trim().contains(firstName + "-")) && (str.trim().contains("-" + lastName))){
		    	myAgents.add(agentParser.parseAgent(str));
		    }
		}
		return myAgents;
	}

	/**
	 * Find agents by state.
	 * 
	 * @param state
	 * @return a list of agents who operate in the state given by state 
	 */
	public List<Agent> getAgentsByState(USState state) {
		List<String> agentHtmlFiles = sfFileReader.findAgentFiles();
		List<Agent> allAgents = getAllAgents();
        List<Agent> agentsByState = new ArrayList<Agent>();
        
        for(Agent a: allAgents) {
            for (Office o : a.getOffices()) {
        		if (o.getAddress().getState() == state) {
        			agentsByState.add(a);
        			continue;
        		}
        	}
        }
        return agentsByState;
	}

	public List<Agent> getAllAgents() {
		List<String> agentFiles = sfFileReader.findAgentFiles();
		List<Agent> agents = new ArrayList<Agent>();
		for (String str: agentFiles) {
		    agents.add(agentParser.parseAgent(str));
		}
		return agents;
	}
	
	public Map<String, List<Agent>> getAllAgentsByUniqueFullName() {
		return null;
	}

	public String mostPopularFirstName() {
		return null;

	}

	public String mostPopularLastName() {
		return null;

	}

	public String mostPopularSuffix() {
		return null;

	}
}
