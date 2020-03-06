package school.first_author;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageExample;
import de.tu_darmstadt.stg.mudetect.aug.model.APIUsageGraph;
import de.tu_darmstadt.stg.mudetect.aug.model.Edge;
import de.tu_darmstadt.stg.mudetect.aug.model.Node;
import de.tu_darmstadt.stg.mudetect.aug.model.data.ConstantNode;
import de.tu_darmstadt.stg.mudetect.aug.model.data.VariableNode;
import de.tu_darmstadt.stg.mudetect.aug.visitors.BaseAUGLabelProvider;
import school.first_author.EnhancedAUG;

/**
 * THe GSpan software expects the graph in a certain format.
 * We'll give it the graph in that format using the code here.
 * @author first_author
 *
 */
public class SubgraphMiningFormatter {

//	public static void convert(Collection<APIUsageExample> augs, int i, Map<String, Integer> vertexLabels, Map<String, Integer> edgeLabels, BufferedWriter writer) throws IOException {
//		// along the way,
//		// we collect the labels of vertices and edges
//
//		
//		for (APIUsageExample aug : augs) {
//			writer.write("t " + "# " + i + "\n");
//
//			Map<Node, Integer> vertexNumbers = new HashMap<>();
//			
//			int nodeNumber = 0;
//			for (Node vertex : aug.vertexSet()) {
//				 String nodeLabel = new BaseAUGLabelProvider().getLabel(vertex);
//				 
//				 if (!vertexLabels.containsKey(nodeLabel)) {
//					 vertexLabels.put(nodeLabel, vertexLabels.size());	 
//				 }
//				 int nodeLabelIndex = vertexLabels.get(nodeLabel);
//				 
//				 writer.write("v " + nodeNumber + " " + nodeLabelIndex+ "\n");
//				 
//				 
//				 vertexNumbers.put(vertex, nodeNumber);
//				 
//				 nodeNumber+=1;
//			}
//			
//			//
//			
//			for (Edge edge : aug.edgeSet()) {
//				String edgeLabel = new BaseAUGLabelProvider().getLabel(edge);
//				
//				if (!edgeLabels.containsKey(edgeLabel)) {
//					edgeLabels.put(edgeLabel, edgeLabels.size());	 
//				}
//				int edgeLabelIndex = edgeLabels.get(edgeLabel);
//				 
//				
//				int sourceNumber = vertexNumbers.get(edge.getSource());
//				int targetNumber = vertexNumbers.get(edge.getTarget());
//				
//				writer.write("e " + sourceNumber + " " + targetNumber + " " + edgeLabelIndex+ "\n");
//			}
//			i++;
//		}
//		
//	}
//	
	/**
	 * This mutates vertexLabels and edgeLabels
	 * @param eaugs
	 * @param type
	 * @param i
	 * @param vertexLabels
	 * @param edgeLabels
	 * @param label
	 * @param quantity
	 * @param writer
	 * @throws IOException
	 */
	public static int convert(Collection<EnhancedAUG> eaugs, Class<?> type ,int i, 
			Map<String, Integer> vertexLabels, Map<String, Integer> edgeLabels, 
			String fileId,
			Map<String, String> labels, int quantity, String subIdentifier, 
			BufferedWriter writer, BufferedWriter idMappingWriter) throws IOException {
		
		if (edgeLabels.isEmpty()) {
			initEdgeLabels(edgeLabels);
		}
		
		// along the way,
		// we collect the labels of vertices and edges
		
		for (EnhancedAUG eaug : eaugs) {
			APIUsageGraph aug = eaug.aug;
			
			if (aug.vertexSet().size() == 0) {
				continue;
			}
			
			// 
			String labelId = fileId + " - " + eaug.aug.name;
			String label = labels.get(labelId);
			if (label == null) {
				System.out.println("omitted due to missing label");
				continue;
			}
//			if (label == null) throw new RuntimeException("missing label!");
			
			
			writer.write("t " + "# " + i + " " + label + " " + quantity + "\n");
			if (idMappingWriter != null) {
				idMappingWriter.write(subIdentifier + fileId + "," + i + "," + labelId + "\n");
			}

			Map<Node, Integer> vertexNumbers = new HashMap<>();
			
			int nodeNumber = 0;
			nodeNumber = writeNodesInAug(vertexLabels, writer, aug, vertexNumbers, nodeNumber);
			
			Map<Node, Node> equivalentNodeToJoinPoint = new HashMap<>();
			for (APIUsageGraph relAug : eaug.related) {
				Node nodeToJoinAt = eaug.relatedJoinPoint.get(relAug);
				
				// iterate through current API usage graph to find same node
				Node equivalentNode = null;
				for (Node node : relAug.vertexSet()) {
					if (node instanceof ConstantNode && nodeToJoinAt instanceof ConstantNode) {
						ConstantNode constantNode = (ConstantNode) node;
						ConstantNode constantNodeToJointAt =(ConstantNode) nodeToJoinAt;
						
						if (constantNode.getName().equals(constantNodeToJointAt.getName())) {
							equivalentNode = constantNode;
							equivalentNodeToJoinPoint.put(equivalentNode, constantNodeToJointAt);
						}
					}
					else if (node instanceof 	VariableNode && nodeToJoinAt instanceof VariableNode) {
						VariableNode variableNode = (VariableNode) node;
						VariableNode variableNodeAtJoinAt = (VariableNode) nodeToJoinAt;
						
						if (variableNode.getName().equals(variableNodeAtJoinAt.getName())) {
							equivalentNode = variableNode;
							equivalentNodeToJoinPoint.put(equivalentNode, variableNodeAtJoinAt);
						}
					} else {
						continue;
					}
				}
				
				// write nodes of related graph
				nodeNumber = writeNodesInAug(vertexLabels, writer, relAug, vertexNumbers, nodeNumber, equivalentNode, nodeToJoinAt);				
			}
			// write nodes for the interface
			for (String interfaceObj : eaug.interfaces) {
				String interfaceString = "interface_" + interfaceObj;
				if (!vertexLabels.containsKey(interfaceString)) {
					 vertexLabels.put(interfaceString, vertexLabels.size());	 
				 }
				 int nodeLabelIndex = vertexLabels.get(interfaceString);
				 
				 writer.write("v " + nodeNumber + " " + nodeLabelIndex+ "\n");
				 
				 ConstantNode interfaceNode = new ConstantNode("DummyInterfaceType", "Interface", interfaceString); 
				 vertexNumbers.put(interfaceNode, nodeNumber);
				 
				 nodeNumber+=1;
			}
			
			//
			writeEdgesInAug(edgeLabels, writer, aug, vertexNumbers);
			for (APIUsageGraph relAug : eaug.related) {
				writeEdgesInAug(edgeLabels, writer, relAug, vertexNumbers, equivalentNodeToJoinPoint);				
			}
			
			writer.write("-\n");
			
			i++;
		}
		
		return i;
	}

	private static void initEdgeLabels(Map<String, Integer> edgeLabels) {
		edgeLabels.put("throw", 0);
		edgeLabels.put("def", 1);
		edgeLabels.put("hdl", 2);
		edgeLabels.put("contains", 3);
		edgeLabels.put("recv", 4);
		edgeLabels.put("finally", 5);
		edgeLabels.put("sel", 6);
		edgeLabels.put("rep", 7);
		edgeLabels.put("sync", 8);
		edgeLabels.put("para", 9);
		edgeLabels.put("order", 10);
		
		edgeLabels.put("order_rev", 11);
	}

	private static void writeEdgesInAug(Map<String, Integer> edgeLabels, BufferedWriter writer, APIUsageGraph aug,
			Map<Node, Integer> vertexNumbers) throws IOException {
		
		writeEdgesInAug(edgeLabels, writer, aug, vertexNumbers, null);
	}
	
	private static void writeEdgesInAug(Map<String, Integer> edgeLabels, BufferedWriter writer, APIUsageGraph aug,
			Map<Node, Integer> vertexNumbers, Map<Node, Node> equivalentNodeToJoinPoint) throws IOException {
	
		
		for (Edge edge : aug.edgeSet()) {
			String edgeLabel = new BaseAUGLabelProvider().getLabel(edge);
			
			if (!edgeLabels.containsKey(edgeLabel)) {
				edgeLabels.put(edgeLabel, edgeLabels.size());
			}
			
			int edgeLabelIndex = edgeLabels.get(edgeLabel);			 
			
			int sourceNumber = getNodenumber(vertexNumbers, equivalentNodeToJoinPoint, edge.getSource());
			
			int targetNumber = getNodenumber(vertexNumbers, equivalentNodeToJoinPoint, edge.getTarget());
			
			writer.write("e " + sourceNumber + " " + targetNumber + " " + edgeLabelIndex+ "\n");
			
			
			if (edgeLabel.equals("order")) {
				edgeLabelIndex = edgeLabels.get("order_rev");	
				sourceNumber = getNodenumber(vertexNumbers, equivalentNodeToJoinPoint, (edge.getTarget()));
				targetNumber = getNodenumber(vertexNumbers, equivalentNodeToJoinPoint, (edge.getSource()));
				
				writer.write("e " + sourceNumber + " " + targetNumber + " " + edgeLabelIndex+ "\n");
				
			}
		}
	}

	private static int getNodenumber(Map<Node, Integer> vertexNumbers, Map<Node, Node> equivalentNodeToJoinPoint,
			Node source) {
		if (equivalentNodeToJoinPoint == null) {
			return vertexNumbers.get(source);
		}
		
		int number;
		if (equivalentNodeToJoinPoint.containsKey(source)) {
			if (!vertexNumbers.containsKey(equivalentNodeToJoinPoint.get(source))) {
				throw new RuntimeException("Uhhh This shouldn't happen! " + source.getId());
			}
			number = vertexNumbers.get( 
					equivalentNodeToJoinPoint.get(source)
					);
		} else {
			number = vertexNumbers.get(source);
		}
		return number;
	}

	private static int writeNodesInAug(Map<String, Integer> vertexLabels, BufferedWriter writer, APIUsageGraph aug,
			Map<Node, Integer> vertexNumbers, int nodeNumber) throws IOException {
		 return writeNodesInAug(vertexLabels, writer, aug, vertexNumbers, nodeNumber, null,  null);
		
	}
	
	private static int writeNodesInAug(Map<String, Integer> vertexLabels, BufferedWriter writer, APIUsageGraph aug,
			Map<Node, Integer> vertexNumbers, int nodeNumber, Node nodeToReplace, Node nodeToJoinAt) throws IOException {
		for (Node vertex : aug.vertexSet()) {
			if (vertex != null && nodeToJoinAt != null && vertex == nodeToReplace) {
				continue;
			}
			
			 String nodeLabel = new BaseAUGLabelProvider().getLabel(vertex);
			 
			 if (!vertexLabels.containsKey(nodeLabel)) {
				 vertexLabels.put(nodeLabel, vertexLabels.size());	 
			 }
			 int nodeLabelIndex = vertexLabels.get(nodeLabel);
			 
			 writer.write("v " + nodeNumber + " " + nodeLabelIndex+ "\n");
			 
			 
			 vertexNumbers.put(vertex, nodeNumber);
			 
			 nodeNumber+=1;
		}
		
		return nodeNumber;
	}
	
}
