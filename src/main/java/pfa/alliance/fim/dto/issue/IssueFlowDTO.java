package pfa.alliance.fim.dto.issue;

import java.util.List;

public class IssueFlowDTO {

	private List<IssueStateRelationDTO> relations;

	private String languageCode = "1"; 
	
	public List<IssueStateRelationDTO> getRelations() {
		return relations;
	}

	public void setRelations(List<IssueStateRelationDTO> relations) {
		this.relations = relations;
	}
	
	public String getDiagram(){
		StringBuilder stringBuilder = new StringBuilder();
		for(IssueStateRelationDTO relation : relations){
			stringBuilder.append(relation.getInitialState().getLocalizedName().get(languageCode));
			if(relation.isBidirectional()){
				stringBuilder.append("<-->");
			}else{
				stringBuilder.append("-->");
			}
		}
		stringBuilder.append(relations.get(relations.size() - 1 ).getNextState().getLocalizedName().get(languageCode));
		return stringBuilder.toString();
	}

}
