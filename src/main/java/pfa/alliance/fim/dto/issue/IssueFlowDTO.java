package pfa.alliance.fim.dto.issue;

import java.util.ArrayList;
import java.util.List;

public class IssueFlowDTO {

	private List<IssueStateRelationDTO> relations;
	
	public List<IssueStateRelationDTO> getRelations() {
		return relations;
	}

	public void setRelations(List<IssueStateRelationDTO> relations) {
		this.relations = relations;
	}
	
	public List<IssueStateDTO> getNextStates(IssueStateDTO issueStateDTO){
		List<IssueStateDTO> nextStates = new ArrayList<IssueStateDTO>();
		
		for(IssueStateRelationDTO relation : relations){
			if(relation.getFromState().equals(issueStateDTO)){
				nextStates.add(relation.getNextState());
			}else if(relation.isBidirectional() && relation.getNextState().equals(issueStateDTO) ){
				nextStates.add(relation.getFromState());
			}
		}
		
		return nextStates;
	}
	
	public IssueStateDTO getInitialState(){
		for(IssueStateRelationDTO relation : relations){
			if(relation.getFromState().isInitialState()){
				return relation.getFromState();
			}
		}
		
		return null;
	}
	
	public String getDiagram(){
		StringBuilder stringBuilder = new StringBuilder();
		for(IssueStateRelationDTO relation : relations){
			stringBuilder.append(relation.getFromState().getName());
			if(relation.isBidirectional()){
				stringBuilder.append("<-->");
			}else{
				stringBuilder.append("-->");
			}
		}
		stringBuilder.append(relations.get(relations.size() - 1 ).getNextState().getName());
		return stringBuilder.toString();
	}

}
